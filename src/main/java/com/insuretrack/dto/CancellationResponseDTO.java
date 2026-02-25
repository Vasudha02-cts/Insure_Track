package com.insuretrack.dto;

import com.insuretrack.entity.enums.CancellationStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CancellationResponseDTO {
    public Long cancellationID;
    public Long policyID;
    public String reason;
    public LocalDate effectiveDate;
    public BigDecimal refundAmount;
    public CancellationStatus status;
}
