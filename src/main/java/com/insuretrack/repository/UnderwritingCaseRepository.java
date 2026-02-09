package com.insuretrack.repository;

import com.insuretrack.entity.UnderwritingCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnderwritingCaseRepository extends JpaRepository<UnderwritingCase, Long> {
    List<UnderwritingCase> findByDecision(UnderwritingCase.DecisionStatus decision);
}