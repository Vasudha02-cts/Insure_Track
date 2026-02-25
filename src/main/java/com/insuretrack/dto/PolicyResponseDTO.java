package com.insuretrack.dto;

import com.insuretrack.entity.enums.PolicyStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PolicyResponseDTO {
    private Long policyID;
    private Long quoteID;
    private String policyNumber;
    private Long customerID;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private Double totalPremium; // Changed to Double to match common entity usage
    private PolicyStatus status;
}