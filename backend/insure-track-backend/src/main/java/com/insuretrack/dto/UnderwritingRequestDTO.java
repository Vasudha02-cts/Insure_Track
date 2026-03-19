package com.insuretrack.dto;

import com.insuretrack.entity.enums.UnderwritingDecision; // APPROVE, DECLINE, REFER
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UnderwritingRequestDTO {
    @NotNull private Long quoteID;
    @NotNull private Long underwriterID;
    @NotNull private UnderwritingDecision decision;
    private String riskAssessmentNotes;
    private String conditions;
}