package com.insuretrack.underwriting.service;

import com.insuretrack.underwriting.dto.UnderwritingDecisionDTO;
import com.insuretrack.underwriting.dto.UnderwritingRequestDTO;
import com.insuretrack.underwriting.dto.UnderwritingResponseDTO;

import java.util.List;

public interface UnderwritingService {
    UnderwritingResponseDTO createCase(Long quoteId);
    List<UnderwritingResponseDTO> getPendingCases();
    UnderwritingResponseDTO getCase(Long caseId);
    UnderwritingResponseDTO makeDecision(Long caseId, UnderwritingDecisionDTO decisionDTO);

    //void createCaseFromQuote(Long quoteId);
}
