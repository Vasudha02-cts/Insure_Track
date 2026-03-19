package com.insuretrack.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Beneficiary")
@Data
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beneficiaryID;
    private String name;
    private String relationship;
    private Double percentageShare;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;
}