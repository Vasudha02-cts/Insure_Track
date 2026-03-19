package com.insuretrack.dto;

import com.insuretrack.entity.enums.EndorsementChangeType;
import com.insuretrack.entity.enums.EndorsementStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class EndorsementResponseDTO {
    private Long endorsementID;
    private Long policyID;        // Added for the toResponse method
    private String changeType;    // Added for the toResponse method
    private LocalDate effectiveDate; // Added for the toResponse method
    private BigDecimal premiumDelta; // Changed to BigDecimal to match Entity
    private String status;
}