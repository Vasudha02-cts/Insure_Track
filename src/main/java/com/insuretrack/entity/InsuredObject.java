package com.insuretrack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "InsuredObject")
public class InsuredObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectID;
    private String objectType;
    @Column(columnDefinition = "JSON")
    private String detailsJSON;
    private Double valuation;
    private Double riskScore;
    private String status;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;
}