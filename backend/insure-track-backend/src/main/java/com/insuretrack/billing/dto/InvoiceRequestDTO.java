package com.insuretrack.billing.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class InvoiceRequestDTO {
    private Double amount;
    private LocalDate dueDate;
}
