package com.insuretrack.service.impl;

import com.insuretrack.dto.InvoiceResponseDTO;
import com.insuretrack.dto.PaymentRequestDTO;
import com.insuretrack.entity.Invoice;
import com.insuretrack.entity.Payment;
import com.insuretrack.entity.Policy;
import com.insuretrack.entity.enums.PaymentStatus;
import com.insuretrack.mapper.BillingMapper; // Use your mapper
import com.insuretrack.repository.InvoiceRepository;
import com.insuretrack.repository.PaymentRepository;
import com.insuretrack.repository.PolicyRepository;
import com.insuretrack.repository.RefundRepository;
import com.insuretrack.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final PolicyRepository policyRepository;
    private final BillingMapper billingMapper; // Injected Mapper

    @Override
    @Transactional
    public void processPayment(PaymentRequestDTO request) {
        Invoice invoice = invoiceRepository.findById(request.getInvoiceID())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        // 1. Create and Save Payment
        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(request.getAmount());
        payment.setPaidDate(LocalDateTime.now()); // Changed to LocalDateTime to match entity
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);

        // 2. Update Invoice Status
        invoice.setStatus("Paid");
        invoiceRepository.save(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> getInvoicesByPolicy(Long policyID) {
        // Fetch invoices for the policy and use Mapper to return DTOs
        return invoiceRepository.findByPolicy_PolicyID(policyID)
                .stream()
                .map(billingMapper::toInvoiceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double calculateCancellationRefund(Long policyID) {
        Policy policy = policyRepository.findById(policyID)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        // 1. Calculate days
        long totalDays = ChronoUnit.DAYS.between(policy.getEffectiveDate(), policy.getExpiryDate());
        long daysUsed = ChronoUnit.DAYS.between(policy.getEffectiveDate(), LocalDate.now());
        long unusedDays = Math.max(0, totalDays - daysUsed);

        if (totalDays <= 0) return 0.0;

        // 2. Perform BigDecimal Math
        // We need: (Premium / totalDays) * unusedDays * 0.90 (penalty)

        BigDecimal premium = policy.getPremium();
        BigDecimal totalDaysBD = BigDecimal.valueOf(totalDays);
        BigDecimal unusedDaysBD = BigDecimal.valueOf(unusedDays);
        BigDecimal penaltyFactor = new BigDecimal("0.90");

        // Calculation: (Premium / TotalDays) with 4 decimal precision for the rate
        BigDecimal dailyRate = premium.divide(totalDaysBD, 4, RoundingMode.HALF_UP);

        // Final Refund: Rate * UnusedDays * 0.90
        BigDecimal proRataRefund = dailyRate.multiply(unusedDaysBD).multiply(penaltyFactor);

        // 3. Round to 2 decimals for the final return
        return proRataRefund.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Logic for the Scheduler to call
     */
    @Transactional
    public void checkAndMarkOverdueInvoices() {
        List<Invoice> overdue = invoiceRepository.findByStatusAndDueDateBefore("Open", LocalDate.now());
        overdue.forEach(inv -> inv.setStatus("Overdue"));
        invoiceRepository.saveAll(overdue);
    }
}