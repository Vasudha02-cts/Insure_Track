package com.insuretrack.billing.dto;

import com.insuretrack.common.enums.InvoiceStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class InvoiceResponseDTO {
    private Long invoiceId;
    private Long policyId;
    private Double amount;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private InvoiceStatus status;
}
