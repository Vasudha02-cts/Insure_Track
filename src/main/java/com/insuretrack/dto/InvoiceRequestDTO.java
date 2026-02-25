package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InvoiceRequestDTO {
    private Long policyID;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private Double amount;
    private String status;
}