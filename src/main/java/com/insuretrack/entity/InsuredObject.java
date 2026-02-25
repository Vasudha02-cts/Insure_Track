package com.insuretrack.entity;

import com.insuretrack.entity.enums.CommonStatus;
import com.insuretrack.entity.enums.InsuredObjectType;
import com.insuretrack.entity.enums.ObjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.insuretrack.entity.enums.InsuredObjectType;

@Data
@Entity
public class InsuredObject {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectID;

    @ManyToOne @JoinColumn(name = "customerid")
    private Customer customer;

    private String detailsJSON; // Make, Model, Year

    @Enumerated(EnumType.STRING)
    private InsuredObjectType objectType; // VEHICLE, PROPERTY

    private Double valuation; // Set by Agent
    private Double riskScore; // Set by Agent

    @Enumerated(EnumType.STRING)
    private ObjectStatus status; // ACTIVE, INACTIVE
}