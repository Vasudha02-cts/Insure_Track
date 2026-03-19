package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Long paymentID;
    private Long invoiceID;
    private Double amount;
    private LocalDateTime paidDate; // Changed from LocalDate to LocalDateTime
    private String method;
    private String status;
}