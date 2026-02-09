package com.insuretrack.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Coverage")
@Data
@NoArgsConstructor
public class Coverage {



    // Internal Enum
    public enum CoverageType { Liability, Collision, Fire, Hospitalization, Term }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CoverageID")
    private Long coverageID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    @JsonBackReference
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "CoverageType")
    private CoverageType coverageType;

    @Column(name = "LimitValue")
    private Double limit;

    @Column(name = "Deductible")
    private Double deductible;
}