package com.insuretrack.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Beneficiary")
@Data
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


}