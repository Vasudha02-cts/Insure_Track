package com.insuretrack.policy.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EndorsementResponseDTO {
    private Long endorsementId;
    private Long policyId;
    private String changeType;
    private Double premiumData;
    private LocalDate effectiveDate;
    private String status;
}
