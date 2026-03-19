package com.insuretrack.underwriting.dto;

import com.insuretrack.common.enums.UnderwritingDecision;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UnderwritingResponseDTO {
    private Long uwCaseId;
    private Long quoteId;
//    private String status;
    private String decisionNotes;
    private LocalDateTime decisionDate;
}
