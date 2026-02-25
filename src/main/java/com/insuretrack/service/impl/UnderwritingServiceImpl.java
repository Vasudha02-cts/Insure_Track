package com.insuretrack.service.impl;

import com.insuretrack.dto.UnderwritingRequestDTO;
import com.insuretrack.dto.UnderwritingResponseDTO;
import com.insuretrack.entity.Notification;
import com.insuretrack.entity.Quote;
import com.insuretrack.entity.UnderwritingCase;
import com.insuretrack.entity.enums.QuoteStatus;
import com.insuretrack.entity.enums.UnderwritingDecision;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.UnderwritingMapper; // Added Mapper
import com.insuretrack.repository.NotificationRepository;
import com.insuretrack.repository.QuoteRepository;
import com.insuretrack.repository.UnderwritingCaseRepository;
import com.insuretrack.service.PolicyService;
import com.insuretrack.service.UnderwritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UnderwritingServiceImpl implements UnderwritingService {

    private final UnderwritingCaseRepository uwRepository;
    private final QuoteRepository quoteRepository;
    private final NotificationRepository notificationRepository;
    private final UnderwritingMapper underwritingMapper; // Inject Mapper
    private final PolicyService policyService;

    @Override
    @Transactional
    public UnderwritingResponseDTO reviewQuote(UnderwritingRequestDTO request) {
        // 1. Fetch Quote
        Quote quote = quoteRepository.findById(request.getQuoteID())
                .orElseThrow(() -> new NotFoundException("Quote not found with ID: " + request.getQuoteID()));

        // 2. Handle Rejection (DECLINE)
        if (request.getDecision() == UnderwritingDecision.DECLINE) {
            Notification note = new Notification();
            note.setUserID(quote.getCustomer().getUser().getUserID());
            note.setCategory("Underwriting");
            note.setMessage("Quote declined: " + request.getRiskAssessmentNotes());
            note.setCreatedDate(LocalDateTime.now());
            note.setStatus("Unread");
            notificationRepository.save(note);

            quoteRepository.delete(quote);

            UnderwritingResponseDTO declineResponse = new UnderwritingResponseDTO();
            declineResponse.setDecision(UnderwritingDecision.DECLINE);
            declineResponse.setRiskAssessmentNotes("Quote deleted after decline.");
            return declineResponse;
        }

        // 3. Handle Approval (APPROVE)
        quote.setStatus(QuoteStatus.Approved);
        quoteRepository.save(quote);

        // --- CRITICAL STEP: GENERATE INVOICE ---
        // 4. Call PolicyService to create the first pro-forma invoice for this quote
        policyService.prepareForBinding(quote.getQuoteID());

        // 5. Create Underwriting Case record
        UnderwritingCase uwCase = new UnderwritingCase();
        uwCase.setQuote(quote);
        uwCase.setUnderwriterID(request.getUnderwriterID());
        uwCase.setDecision(request.getDecision().name());
        uwCase.setRiskAssessment(request.getRiskAssessmentNotes());
        uwCase.setConditions(request.getConditions());
        uwCase.setDecisionDate(LocalDateTime.now());

        UnderwritingCase saved = uwRepository.save(uwCase);

        // 6. Return Response
        return underwritingMapper.toResponse(saved);
    }

    @Override
    public UnderwritingResponseDTO getHistoryByQuote(Long quoteId) {
        UnderwritingCase uwCase = uwRepository.findByQuote_QuoteID(quoteId)
                .orElseThrow(() -> new NotFoundException("No Underwriting history found for Quote: " + quoteId));
        return underwritingMapper.toResponse(uwCase);
    }
}