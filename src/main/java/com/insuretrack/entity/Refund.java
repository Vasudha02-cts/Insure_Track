package com.insuretrack.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refundID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaymentID")
    private Payment payment;

    private Double amount;
    private LocalDateTime processedDate;
    private String reason;
    private String status; // Initiated/Completed/Failed [cite: 238]
}