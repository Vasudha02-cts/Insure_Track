package com.insuretrack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "RatingRule")
public class RatingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RuleID")
    private Long ruleID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(name = "Factor")
    private String factor; // Age, Location, etc. [cite: 107]

    @Column(name = "Weight")
    private Double weight;

    @Column(name = "Expression", columnDefinition = "TEXT")
    private String expression;

    // Getters and Setters
}