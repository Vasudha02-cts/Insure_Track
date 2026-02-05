package com.insuretrack.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Renewal")
public class Renewal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long renewalID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PolicyID")
    private Policy policy;
    private Double proposedPremium;
    @Column(columnDefinition = "TEXT")
    private String proposedCoveragesJSON;
    private LocalDate offerDate;
    private String status; // Offered/Accepted/Declined [cite: 166]
}
