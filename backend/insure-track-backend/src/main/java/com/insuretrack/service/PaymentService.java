package com.insuretrack.service;

import com.insuretrack.dto.PaymentReceiptDTO;
import com.insuretrack.dto.PaymentRequestDTO;
import com.insuretrack.dto.PaymentResponseDTO;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO dto);
    PaymentResponseDTO getPaymentById(Long id);
    List<PaymentResponseDTO> getAllPayments();

    PaymentReceiptDTO getReceipt(Long id);
    List<PaymentResponseDTO> getPaymentsByInvoice(Long invoiceID);
}