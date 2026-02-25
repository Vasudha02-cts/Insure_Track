package com.insuretrack.entity;

import com.insuretrack.entity.enums.ClaimStatus;
import com.insuretrack.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InvoiceID", nullable = false)
    private Invoice invoice;

    private Double amount;
    private LocalDateTime paidDate;
    private String method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // Use PaymentStatus instead of ClaimStatus!
}