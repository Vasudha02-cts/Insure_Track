package com.insuretrack.entity;

import com.insuretrack.entity.enums.CancellationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Table: Cancellation
 * Columns: CancellationID, PolicyID, Reason, EffectiveDate, RefundAmount, Status
 * Relationship:
 *  - Many cancellations belong to one Policy (PolicyID -> Policy.PolicyID)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cancellation")
public class Cancellation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CancellationID")
    private Long cancellationID;

    @NotNull
    @Column(name = "PolicyID", nullable = false)
    private Long policyID;

    @NotBlank
    @Column(name = "Reason", nullable = false, length = 255)
    private String reason;

    @NotNull
    @Column(name = "EffectiveDate", nullable = false)
    private LocalDate effectiveDate;

    @PositiveOrZero
    @Column(name = "RefundAmount", precision = 18, scale = 2, nullable = false)
    private BigDecimal refundAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", length = 20, nullable = false)
    private CancellationStatus status;

    // ---- Association to Policy (read-only, lazy) ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PolicyID", referencedColumnName = "PolicyID",
            insertable = false, updatable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Policy policy;
}