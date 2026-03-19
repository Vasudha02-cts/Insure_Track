package com.insuretrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CancellationRequestDTO {

    @NotNull(message = "Policy ID is mandatory")
    public Long policyID;

    @NotBlank(message = "Cancellation reason must be provided")
    @Size(min = 10, message = "Please provide a detailed reason (at least 10 characters)")
    public String reason;

    // Optional: Date requested can be sent or defaulted to today in service
    public String effectiveDate;
}