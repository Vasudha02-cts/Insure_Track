package com.insuretrack.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RatingRule")
@Data
@NoArgsConstructor
public class RatingRule {

    // Strictly defined by your module requirements
    public enum Factor { Age, Location, VehicleType, ClaimsHistory }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RuleID")
    private Long ruleID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    @JsonBackReference
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "Factor")
    private Factor factor; //

    @Column(name = "Weight")
    private Double weight; //

    @Column(name = "Expression", columnDefinition = "TEXT")
    private String expression; // Formula/JSON
}