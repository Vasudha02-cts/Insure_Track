package com.insuretrack.billing.dto;

import com.insuretrack.common.enums.RefundStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RefundResponseDTO {
    private Long refundId;
    private Long paymentId;
    private Double amount;
    private LocalDate processedDate;
    private String reason;
    private RefundStatus status;
}
