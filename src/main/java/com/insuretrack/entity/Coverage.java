package com.insuretrack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Coverage")
public class Coverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CoverageID")
    private Long coverageID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(name = "CoverageType")
    private String coverageType; // Liability, Collision, etc. [cite: 101]

    @Column(name = "LimitValue") // 'Limit' is a SQL reserved keyword
    private Double limit;

    @Column(name = "Deductible")
    private Double deductible;

    // Getters and Setters
}