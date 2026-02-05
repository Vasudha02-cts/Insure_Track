package com.insuretrack.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Claim")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimID;
    private LocalDate incidentDate;
    private LocalDate reportedDate;
    private String claimType;
    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "PolicyID")
    private Policy policy;
}
