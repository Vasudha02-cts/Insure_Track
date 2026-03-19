package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PaymentReceiptDTO {
    private String receiptNumber;
    private Long paymentID;
    private Long invoiceID;
    private String customerName;
    private Double amountPaid;
    private String paymentMethod;
    private LocalDate transactionDate;
    private String policyNumber;
    private String status;
}