package com.insuretrack.repository;

import com.insuretrack.entity.UnderwritingCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnderwritingCaseRepository extends JpaRepository<UnderwritingCase, Long> {
    List<UnderwritingCase> findByDecision(String decision);// Queue for underwriters [cite: 117]
    Optional<UnderwritingCase> findByQuote_QuoteID(Long quoteID);
}

