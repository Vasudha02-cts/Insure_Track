package com.insuretrack.entity;

import com.insuretrack.entity.enums.ClaimStatus;
import com.insuretrack.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Settlement")
@Data
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settlementID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClaimID", nullable = false)
    private Claim claim;

    private Double settlementAmount;
    private LocalDateTime settlementDate;
    private String paymentReference;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // Pending, Paid, Failed


}