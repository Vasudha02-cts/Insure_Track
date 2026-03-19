package com.insuretrack.service.impl;

import com.insuretrack.dto.*;
import com.insuretrack.entity.*;
import com.insuretrack.entity.enums.PaymentStatus;
import com.insuretrack.entity.enums.PolicyStatus;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.PaymentMapper;
import com.insuretrack.repository.*;
import com.insuretrack.service.PaymentService;
import com.insuretrack.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final PolicyRepository policyRepository;
    private final PaymentMapper paymentMapper;
    private final PolicyService policyService; // To trigger policy issuance

    @Override
    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO dto) {
        Invoice invoice = invoiceRepository.findById(dto.getInvoiceID())
                .orElseThrow(() -> new NotFoundException("Invoice not found"));

        Payment payment = paymentMapper.toEntity(dto);
        payment.setInvoice(invoice);
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidDate(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        // 1. Mark Invoice as PAID
        invoice.setStatus("PAID");
        invoiceRepository.save(invoice);

        // 2. TRIGGER: "Policy created only after first payment"
        // Logic: If invoice is linked to a Quote and has no Policy yet, issue the Policy
        if (invoice.getPolicy() == null && invoice.getQuote() != null) {
            // This method in PolicyService will generate the Policy Number & Expiry
            policyService.issuePolicyFromQuote(invoice.getQuote().getQuoteID());
        }
        // 3. Logic: If policy was Pending_Payment, flip to Active
        else if (invoice.getPolicy() != null && invoice.getPolicy().getStatus() == PolicyStatus.Pending_Payment) {
            Policy p = invoice.getPolicy();
            p.setStatus(PolicyStatus.Active);
            policyRepository.save(p);
        }

        return paymentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentReceiptDTO getReceipt(Long paymentID) {
        return paymentRepository.findById(paymentID)
                .map(paymentMapper::toReceiptDTO)
                .orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByInvoice(Long invoiceID) {
        return paymentRepository.findByInvoice_InvoiceID(invoiceID).stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }
}