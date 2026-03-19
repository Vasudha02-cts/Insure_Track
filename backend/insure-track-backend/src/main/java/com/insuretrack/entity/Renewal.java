package com.insuretrack.entity;

import com.insuretrack.entity.enums.RenewalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Table: Renewal
 * Columns: RenewalID, PolicyID, ProposedPremium, ProposedCoveragesJSON, OfferDate, Status
 * Relationship:
 *  - Many renewals belong to one Policy (PolicyID -> Policy.PolicyID)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Renewal")
public class Renewal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RenewalID")
    private Long renewalID;

    @NotNull
    @Column(name = "PolicyID", nullable = false)
    private Long policyID;

    @NotNull
    @PositiveOrZero
    @Column(name = "ProposedPremium", precision = 18, scale = 2, nullable = false)
    private BigDecimal proposedPremium;

    @Lob
    @Column(name = "ProposedCoveragesJSON", columnDefinition = "TEXT")
    private String proposedCoveragesJSON;

    @NotNull
    @Column(name = "OfferDate", nullable = false)
    private LocalDate offerDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", length = 20, nullable = false)
    private RenewalStatus status;

    // ---- Association to Policy (read-only, lazy) ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PolicyID", referencedColumnName = "PolicyID",
            insertable = false, updatable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Policy policy;
}