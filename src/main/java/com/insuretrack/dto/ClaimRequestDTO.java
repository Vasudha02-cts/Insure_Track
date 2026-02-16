package com.insuretrack.dto;

import lombok.Data;

@Data
public class ClaimRequestDTO {
    private Long policyID;     // To link the claim to Policy 1
    private String claimType;  // AUTO, HEALTH, etc.
    private String description;
    private String incidentDate; // Format: YYYY-MM-DD
}