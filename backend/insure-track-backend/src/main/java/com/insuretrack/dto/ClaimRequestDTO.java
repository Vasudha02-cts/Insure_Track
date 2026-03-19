package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClaimRequestDTO {
    private Long policyID;
    private String claimType;  // AUTO
    private String description;
    private LocalDate incidentDate; // Changed from String to LocalDate
}