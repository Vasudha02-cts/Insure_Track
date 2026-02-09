package com.insuretrack.service;

import com.insuretrack.entity.UnderwritingCase;
import java.util.List;

public interface UnderwritingCaseService {
    UnderwritingCase createCase(UnderwritingCase uwCase);
    UnderwritingCase updateDecision(Long id, UnderwritingCase decisionUpdate);
    List<UnderwritingCase> getCasesByDecision(UnderwritingCase.DecisionStatus status);
    List<UnderwritingCase> getAllCases();
}