package com.insuretrack.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reserve")
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClaimID")
    private Claim claim;

    private Double amount;
    private LocalDateTime setDate;
    private LocalDateTime updatedDate;
    private String status; // Open/Adjusted/Released [cite: 192]
}
