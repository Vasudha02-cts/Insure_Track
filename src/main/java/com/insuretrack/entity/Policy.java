package com.insuretrack.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Policy")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyID;
    private Long quoteID;
    private String policyNumber;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private Double premium;
    private String status;
}
