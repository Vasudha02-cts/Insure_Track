package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClaimAssignmentResponseDTO {
    private Long assignmentID;
    private Long claimID;
    private Long adjusterID;
    private LocalDateTime assignmentDate;
    private String priority;
}