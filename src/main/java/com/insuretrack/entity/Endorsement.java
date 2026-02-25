package com.insuretrack.entity;

import com.insuretrack.entity.enums.EndorsementChangeType;
import com.insuretrack.entity.enums.EndorsementStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Table: Endorsement
 * Columns: EndorsementID, PolicyID, ChangeType, EffectiveDate, PremiumDelta, Status
 * Relationship:
 *  - Many endorsements belong to one Policy (PolicyID -> Policy.PolicyID)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Endorsement")
public class Endorsement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EndorsementID")
    private Long endorsementID;

    @NotNull
    @Column(name = "PolicyID", nullable = false)
    private Long policyID;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ChangeType", length = 40, nullable = false)
    private EndorsementChangeType changeType;

    @NotNull
    @Column(name = "EffectiveDate", nullable = false)
    private LocalDate effectiveDate;

    // Can be positive (increase) or negative (decrease), so no @PositiveOrZero here
    @NotNull
    @Column(name = "PremiumDelta", precision = 18, scale = 2, nullable = false)
    private BigDecimal premiumDelta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", length = 20, nullable = false)
    private EndorsementStatus status;

    // ---- Association to Policy (read-only, lazy) ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PolicyID", referencedColumnName = "PolicyID",
            insertable = false, updatable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Policy policy;
}
