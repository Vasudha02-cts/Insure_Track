package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClaimResponseDTO {
    private Long claimID;
    private LocalDate incidentDate;
    private LocalDate reportedDate;
    private String claimType;
    private String description;
    private String status;
}