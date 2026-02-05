package com.insuretrack.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Settlement")
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
    private String status; // Pending, Paid, Failed [cite: 199]

    // Getters and Setters...
}