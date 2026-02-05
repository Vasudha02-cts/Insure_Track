package com.insuretrack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Beneficiary")
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BeneficiaryID")
    private Long beneficiaryID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @Column(name = "Name")
    private String name;

    @Column(name = "Relationship")
    private String relationship;

    @Column(name = "PercentageShare")
    private Double percentageShare;

    // Getters and Setters
}