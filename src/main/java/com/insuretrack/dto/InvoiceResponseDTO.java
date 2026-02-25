package com.insuretrack.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class InvoiceResponseDTO {
    private Long invoiceID;
    private String policyNumber;
    private Double amount;      // (Annual Premium / 12)
    private LocalDate dueDate;
    private String status;      // Open / Paid / Overdue
}