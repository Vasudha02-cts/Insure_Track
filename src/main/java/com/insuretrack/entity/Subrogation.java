package com.insuretrack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Subrogation")
public class Subrogation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subrogationID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClaimID")
    private Claim claim;

    private String counterparty;
    private Double amountSought;
    private String status; // Initiated/Recovered/Closed [cite: 211]
}