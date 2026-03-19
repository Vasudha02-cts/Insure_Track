package com.insuretrack.dto;

import com.insuretrack.entity.enums.EndorsementChangeType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class EndorsementRequestDTO {
    private Long policyID;
    private EndorsementChangeType changeType;
    //private BigDecimal premiumDelta;
    private Long coverageID;
    private LocalDate effectiveDate;
}