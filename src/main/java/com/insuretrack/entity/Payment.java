package com.insuretrack.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private Long paymentID;

    // Relationship: Many payments can be made against one Invoice (e.g., partial payments)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InvoiceID", nullable = false)
    private Invoice invoice;

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "PaidDate")
    private LocalDateTime paidDate;

    @Column(name = "Method")
    private String method; // Card, ACH, UPI, Wallet [cite: 230]

    @Column(name = "Status")
    private String status; // Pending, Completed, Failed [cite: 231]

    // Getters and Setters...
}