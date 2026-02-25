package com.insuretrack.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RefundResponseDTO {
    private Long refundId;
    private Long paymentId;
    private Double amount;
    private LocalDate processedDate;
    private String reason;
    private String status;
}