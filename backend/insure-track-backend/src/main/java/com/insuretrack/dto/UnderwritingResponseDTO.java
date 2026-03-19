package com.insuretrack.dto;

import com.insuretrack.entity.enums.UnderwritingDecision;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UnderwritingResponseDTO {
    private Long uwCaseID;
    private Long quoteID;
    private UnderwritingDecision decision;
    private String riskAssessmentNotes;
    private LocalDateTime decisionDate;
}