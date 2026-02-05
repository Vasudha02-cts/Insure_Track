package com.insuretrack.repository;

import com.insuretrack.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByStatus(String status); // Draft vs Approved [cite: 134]
}
