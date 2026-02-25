package com.insuretrack.repository;

import com.insuretrack.entity.Quote;
import com.insuretrack.entity.enums.QuoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    // Fixed: Matches quote.customer.customerID
    List<Quote> findByCustomer_CustomerID(Long customerID);

    // Standard status lookup
    List<Quote> findByStatus(QuoteStatus status);
}