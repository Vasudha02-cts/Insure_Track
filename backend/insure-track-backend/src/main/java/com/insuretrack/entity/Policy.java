package com.insuretrack.entity;

import com.insuretrack.entity.enums.PolicyStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Table: Policy
 * Columns: PolicyID, QuoteID, PolicyNumber, EffectiveDate, ExpiryDate, Premium, Status
 * Relationship:
 *  - Many policies belong to one Quote (QuoteID -> Quote.QuoteID)
 */

@Data
@Entity
@Table(name = "Policy")
public class Policy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyID;

    @OneToOne @JoinColumn(name = "quote_id")
    private Quote quote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id") // This fixes 'setCustomer'
    private Customer customer;

    private String policyNumber;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private BigDecimal premium;

    @Enumerated(EnumType.STRING)
    private PolicyStatus status;
}