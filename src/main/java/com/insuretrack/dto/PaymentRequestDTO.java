package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDate;
@Data
public class PaymentRequestDTO {
    private Long invoiceID;
    private Double amount;
    private String method;      // UPI, Card, ACH
    private String transactionRef;
}