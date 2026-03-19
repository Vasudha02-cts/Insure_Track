package com.insuretrack.underwriting.service;
import com.insuretrack.common.enums.QuoteStatus;
import com.insuretrack.common.enums.UnderwritingDecision;
import com.insuretrack.policy.service.PolicyService;
import com.insuretrack.quote.entity.Quote;
import com.insuretrack.quote.repository.QuoteRepository;
import com.insuretrack.underwriting.dto.UnderwritingDecisionDTO;
import com.insuretrack.underwriting.dto.UnderwritingResponseDTO;
import com.insuretrack.underwriting.entity.UnderwritingCase;
import com.insuretrack.underwriting.repository.UnderwritingCaseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.insuretrack.underwriting.entity.UnderwritingCase.*;
@Service
@AllArgsConstructor
@Transactional
public class UnderwritingServiceImpl implements UnderwritingService {
    private final UnderwritingCaseRepository underwritingRepository;
    private final QuoteRepository quoteRepository;
    private final PolicyService policyService;
    @Override
    public UnderwritingResponseDTO createCase(Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("Quote not found"));
        UnderwritingCase uwCase = builder()
                .quote(quote)
                .decision(UnderwritingDecision.PENDING)
                .decisionDate(LocalDateTime.now())
                .build();

        return mapToDTO(underwritingRepository.save(uwCase));
    }

    @Override
    public List<UnderwritingResponseDTO> getPendingCases() {
        return underwritingRepository.findAll()
                .stream()
                .filter(c -> c.getDecision() == UnderwritingDecision.PENDING)
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public UnderwritingResponseDTO getCase(Long caseId) {
        UnderwritingCase uwCase = underwritingRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));
        return mapToDTO(uwCase);
    }

    @Override
    public UnderwritingResponseDTO makeDecision(Long caseId, UnderwritingDecisionDTO decisionDTO) {
        UnderwritingCase uwCase = underwritingRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));

        // 1. CHECK FIRST: Validation must happen before any state changes
        if (uwCase.getDecision() != UnderwritingDecision.PENDING) {
            throw new RuntimeException("Decision already made");
        }

        // 2. NOW UPDATE: Safe to proceed because we verified it's PENDING
        UnderwritingDecision decision = UnderwritingDecision.valueOf(decisionDTO.getDecision());
        uwCase.setDecision(decision);
        uwCase.setConditions(decisionDTO.getNotes());

        Quote quote = uwCase.getQuote();
        if (decision == UnderwritingDecision.APPROVE) {
            quote.setStatus(QuoteStatus.APPROVED);
            policyService.issuePolicy(quote.getQuoteId());
        } else {
            // Mapping DECLINE to REJECTED based on your QuoteStatus enum
            quote.setStatus(QuoteStatus.REJECTED);
        }

        // 3. SAVE changes
        return mapToDTO(underwritingRepository.save(uwCase));
    }

    private UnderwritingResponseDTO mapToDTO(UnderwritingCase uwCase) {
        return UnderwritingResponseDTO.builder()
                .uwCaseId(uwCase.getUwCaseId())
                .quoteId(uwCase.getQuote().getQuoteId())
                .decisionNotes(uwCase.getConditions())
                .decisionDate(uwCase.getDecisionDate())
                .build();
    }
}