package com.insuretrack.service;

import com.insuretrack.dto.InvoiceResponseDTO;
import com.insuretrack.dto.PaymentRequestDTO;

import java.util.List;

public interface BillingService {
    void processPayment(PaymentRequestDTO request);
    List<InvoiceResponseDTO> getInvoicesByPolicy(Long policyID);
    Double calculateCancellationRefund(Long policyID);
}