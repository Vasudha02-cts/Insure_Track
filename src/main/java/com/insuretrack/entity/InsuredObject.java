package com.insuretrack.entity;

import com.insuretrack.entity.enums.CommonStatus;
import com.insuretrack.entity.enums.InsuredObjectType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "InsuredObject")
@Data
public class InsuredObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectID;
    @Enumerated(EnumType.STRING)
    private InsuredObjectType objectType;
    @Column(columnDefinition = "TEXT")
    private String detailsJSON;
    private Double valuation;
    private Double riskScore;
    @Enumerated(EnumType.STRING)
    private CommonStatus status;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;
}