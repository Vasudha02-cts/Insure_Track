package com.insuretrack.entity;

import com.insuretrack.entity.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Settlement")
@Data
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SettlementID")
    private Long settlementID;
    // Link to the Claim being paid
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClaimID", nullable = false)
    private Claim claim;

    @Column(name = "SettlementAmount")
    private Double settlementAmount;

    @Column(name = "SettlementDate")
    private LocalDateTime settlementDate;

    @Column(name = "PaymentReference")
    private String paymentReference;

    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private ClaimStatus status; // Pending, Paid, Failed

    // Getters and Setters...
}