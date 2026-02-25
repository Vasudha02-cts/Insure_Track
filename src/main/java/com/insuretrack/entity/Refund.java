package com.insuretrack.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "Refund")
@Data
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RefundID")
    private Long refundId;

    private Double amount;
    private LocalDate processedDate;
    private String reason;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaymentID", nullable = false)
    private Payment payment;
}