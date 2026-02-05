package com.insuretrack.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Quote")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuoteID")
    private Long quoteID;

    // Relationship to Customer [cite: 127]
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    // Relationship to Product [cite: 128]
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    // Relationship to the specific object being insured [cite: 129]
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InsuredObjectID", nullable = false)
    private InsuredObject insuredObject;

    @Column(name = "CoveragesJSON", columnDefinition = "TEXT") // Stores selection of coverages
    private String coveragesJSON;

    @Column(name = "Premium")
    private Double premium; // Calculated cost [cite: 132]

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Column(name = "Status")
    private String status; // Draft/Submitted/Rated/Approved/Expired [cite: 134]

    // Constructors, Getters, and Setters
}