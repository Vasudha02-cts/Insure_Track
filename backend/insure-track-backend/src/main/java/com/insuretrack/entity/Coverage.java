package com.insuretrack.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Coverage")
public class Coverage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coverageID;

    @ManyToOne @JoinColumn(name = "product_id")
    private Product product;

    private String coverageType; // Liability, Collision
    private Double limitAmount;
    private Double deductible;
}