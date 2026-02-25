package com.insuretrack.repository;

import com.insuretrack.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    // Fixed: Navigates Invoice -> Policy -> Customer -> customerID
    List<Invoice> findByPolicy_Customer_CustomerID(Long customerID);

    List<Invoice> findByPolicy_PolicyID(Long policyID);

    List<Invoice> findByStatusAndDueDateBefore(String status, LocalDate date);
    List<Invoice> findByQuote_QuoteID(Long quoteID);
}