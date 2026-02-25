package com.insuretrack.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "RatingRule")
public class RatingRule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleID;

    @ManyToOne @JoinColumn(name = "product_id")
    private Product product;

    private String factor; // Age, VehicleType, ClaimsHistory
    private Double weight;
    private String expression; // e.g., "base * factor + riskscore * 10"
}