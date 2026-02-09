package com.insuretrack.service.impl;

import com.insuretrack.entity.UnderwritingCase;
import com.insuretrack.repository.UnderwritingCaseRepository;
import com.insuretrack.service.UnderwritingCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnderwritingCaseServiceImpl implements UnderwritingCaseService {

    @Autowired private UnderwritingCaseRepository repository;

    @Override
    public UnderwritingCase createCase(UnderwritingCase uwCase) {
        return repository.save(uwCase);
    }

    @Override
    public UnderwritingCase updateDecision(Long id, UnderwritingCase update) {
        UnderwritingCase existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Underwriting Case not found"));

        existing.setDecision(update.getDecision());
        existing.setRiskAssessment(update.getRiskAssessment());
        existing.setConditions(update.getConditions());
        existing.setUnderwriterID(update.getUnderwriterID());

        return repository.save(existing);
    }

    @Override
    public List<UnderwritingCase> getCasesByDecision(UnderwritingCase.DecisionStatus status) {
        // Pass the Enum directly, do NOT use .name()
        return repository.findByDecision(status);
    }

    @Override
    public List<UnderwritingCase> getAllCases() {
        return repository.findAll();
    }
}