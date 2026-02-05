package com.insuretrack.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Endorsement")
public class Endorsement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long endorsementID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PolicyID")
    private Policy policy;

    private String changeType; // AddCoverage/RemoveCoverage/LimitChange [cite: 147]
    private LocalDate effectiveDate;
    private Double premiumDelta;
    private String status; // Draft/Approved/Applied [cite: 151]
}