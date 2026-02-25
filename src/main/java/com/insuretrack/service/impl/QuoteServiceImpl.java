package com.insuretrack.service.impl;

import com.insuretrack.dto.QuoteRequestDTO;
import com.insuretrack.dto.QuoteResponseDTO;
import com.insuretrack.entity.*;
import com.insuretrack.entity.enums.ObjectStatus;
import com.insuretrack.entity.enums.QuoteStatus;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.QuoteMapper;
import com.insuretrack.repository.*;
import com.insuretrack.service.QuoteService;
import com.insuretrack.service.RatingEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;
    private final CustomerRepository customerRepository;
    private final InsuredObjectRepository objectRepository;
    private final RatingEngineService ratingEngine;

    @Override
    @Transactional
    public QuoteResponseDTO createQuote(QuoteRequestDTO dto) {
        // 1. Fetch Object and verify Agent has valuated it (Status MUST be ACTIVE)
        InsuredObject vehicle = objectRepository.findById(dto.getInsuredObjectID())
                .orElseThrow(() -> new NotFoundException("Insured Object not found"));

        if (vehicle.getStatus() != ObjectStatus.ACTIVE) {
            throw new IllegalStateException("Cannot create quote: Insured Object must be valuated and ACTIVE by an Agent.");
        }

        Quote quote = quoteMapper.toEntity(dto);
        quote.setInsuredObject(vehicle);
        quote.setCustomer(vehicle.getCustomer());

        // Initial state
        quote.setStatus(QuoteStatus.Draft);
        quote.setCreatedDate(LocalDate.now());

        return quoteMapper.toResponse(quoteRepository.save(quote));
    }

    @Override
    @Transactional
    public QuoteResponseDTO submitForRating(Long quoteID) {
        Quote quote = quoteRepository.findById(quoteID)
                .orElseThrow(() -> new NotFoundException("Quote not found"));

        // 2. Transition: Draft -> Submitted
        quote.setStatus(QuoteStatus.Submitted);

        // 3. Trigger Rating Engine: Submitted -> Rated
        // Rating calculation logic now uses Collision, Liability, Fire
        Double calculatedPremium = ratingEngine.calculatePremium(quote);
        quote.setPremium(BigDecimal.valueOf(calculatedPremium));
        quote.setStatus(QuoteStatus.Rated);

        return quoteMapper.toResponse(quoteRepository.save(quote));
    }

    @Override
    public QuoteResponseDTO getById(Long quoteID) {
        Quote q = quoteRepository.findById(quoteID)
                .orElseThrow(() -> new NotFoundException("Quote not found"));
        return quoteMapper.toResponse(q);
    }

    @Override
    public List<QuoteResponseDTO> listByCustomer(Long customerID) {
        return quoteRepository.findByCustomer_CustomerID(customerID).stream()
                .map(quoteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuoteResponseDTO updateStatus(Long quoteID, QuoteStatus status) {
        Quote q = quoteRepository.findById(quoteID)
                .orElseThrow(() -> new NotFoundException("Quote not found: " + quoteID));

        // TRIGGER RATING ENGINE
        if (status == QuoteStatus.Submitted) {
            // 1. Calculate the premium using the logic we wrote (Collision + Liability + Fire)
            Double calculatedAmount = ratingEngine.calculatePremium(q);

            // 2. SET THE VALUE into the entity
            q.setPremium(BigDecimal.valueOf(calculatedAmount));

            // 3. Move to 'Rated' so the Underwriter knows it has a price
            q.setStatus(QuoteStatus.Rated);
        } else {
            q.setStatus(status);
        }

        // 4. SAVE the updated entity (this writes the premium to the DB)
        Quote saved = quoteRepository.save(q);
        return quoteMapper.toResponse(saved);
    }
}