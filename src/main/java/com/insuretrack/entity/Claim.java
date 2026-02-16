package com.insuretrack.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.insuretrack.entity.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "Claim")
@Data
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimID;
    private LocalDate incidentDate;
    private LocalDate reportedDate;
    private String claimType;
    private String description;
    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    @ManyToOne
    @JoinColumn(name = "PolicyID")
    @JsonBackReference
    private Policy policy;
}
