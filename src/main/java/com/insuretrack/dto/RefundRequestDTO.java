package com.insuretrack.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RefundRequestDTO {
    @NotNull(message = "Payment ID is required")
    private Long paymentId;

    @NotNull(message = "Refund amount is required")
    @Positive(message = "Refund amount must be positive")
    private Double amount;

    @NotNull(message = "Processed date is required")
    private LocalDate processedDate;

    private String reason;

    @NotNull(message = "Status is required")
    private String status; // INITIATED, COMPLETED, FAILED
}