package com.insuretrack.entity;

import jakarta.persistence.*;
import lombok.Data; // Ensure this is imported
import java.time.LocalDate;

@Entity
@Table(name = "Policy")
@Data // This is crucial for Spring Data JPA to find 'policyNumber'
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyID;

    private Long quoteID;

    // Check this spelling carefully!
    // It must match 'findByPolicyNumber' exactly.
    private String policyNumber;

    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private Double premium;
    private String status;
}
