package com.insuretrack.service.impl;

import com.insuretrack.dto.PolicyRequestDTO;
import com.insuretrack.dto.PolicyResponseDTO;
import com.insuretrack.entity.*;
import com.insuretrack.entity.enums.PolicyStatus;
import com.insuretrack.entity.enums.QuoteStatus;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.PolicyMapper;
import com.insuretrack.repository.*;
import com.insuretrack.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final QuoteRepository quoteRepository;
    private final InvoiceRepository invoiceRepository;
    private final PolicyMapper policyMapper;

    /**
     * Step 1: Prepare for Binding
     * Creates the very first invoice based on the Approved Quote.
     * Policy object is NOT created yet.
     */
    @Override
    @Transactional
    public void prepareForBinding(Long quoteID) {
        Quote quote = quoteRepository.findById(quoteID)
                .orElseThrow(() -> new NotFoundException("Quote not found"));
        // SAFETY CHECK: Prevent the NULL pointer exception
        if (quote.getPremium() == null) {
            throw new IllegalStateException("Cannot approve quote #" + quoteID + " because the premium has not been calculated yet. Please move status to 'Submitted' first.");
        }
        if (quote.getStatus() != QuoteStatus.Approved) {
            throw new IllegalStateException("Quote must be Approved to generate initial invoice.");
        }

        // Create the first pro-forma invoice linked to the QUOTE
        Invoice firstInvoice = new Invoice();
        firstInvoice.setQuote(quote); // Ensure your Invoice entity has a 'quote' field

        BigDecimal totalPremium = quote.getPremium();
        BigDecimal firstAmount = totalPremium.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);

        firstInvoice.setAmount(firstAmount.doubleValue());
        firstInvoice.setIssueDate(LocalDate.now());
        firstInvoice.setDueDate(LocalDate.now().plusDays(7));
        firstInvoice.setStatus("Open");

        invoiceRepository.save(firstInvoice);
    }

    /**
     * Step 2: Actual Issuance
     * Triggered by PaymentServiceImpl when the first payment is successful.
     */
    @Override
    @Transactional
    public void issuePolicyFromQuote(Long quoteID) {
        Quote quote = quoteRepository.findById(quoteID)
                .orElseThrow(() -> new NotFoundException("Quote not found"));

        // 1. Create the Policy Entity
        Policy policy = new Policy();
        policy.setQuote(quote);
        policy.setCustomer(quote.getCustomer());
        policy.setPremium(quote.getPremium());
        policy.setEffectiveDate(LocalDate.now());
        policy.setExpiryDate(LocalDate.now().plusYears(1));
        policy.setStatus(PolicyStatus.Active);

        // 2. Auto-generate Policy Number
        policy.setPolicyNumber("POL-" + LocalDate.now().getYear() + "-" + System.currentTimeMillis() % 10000);

        Policy savedPolicy = policyRepository.save(policy);

        // --- NEW LOGIC: Link the original Quote Invoice to this new Policy ---
        List<Invoice> quoteInvoices = invoiceRepository.findByQuote_QuoteID(quoteID);
        for (Invoice qInv : quoteInvoices) {
            if (qInv.getPolicy() == null) {
                qInv.setPolicy(savedPolicy); // Link it now!
                invoiceRepository.save(qInv);
            }
        }
        // ---------------------------------------------------------------------

        // 3. Generate the remaining 11 invoices
        generateRemainingInvoices(savedPolicy);

        // 4. Finalize Quote status
        quote.setStatus(QuoteStatus.Bound);
        quoteRepository.save(quote);
    }

    private void generateRemainingInvoices(Policy policy) {
        BigDecimal totalPremium = policy.getPremium();
        BigDecimal divisor = new BigDecimal("12");
        BigDecimal monthly = totalPremium.divide(divisor, 2, RoundingMode.HALF_UP);

        BigDecimal totalExceptLast = monthly.multiply(new BigDecimal("11"));
        BigDecimal lastInstallment = totalPremium.subtract(totalExceptLast);

        List<Invoice> invoiceList = new ArrayList<>();
        // Start from index 1 because index 0 (first month) was already paid as a Quote Invoice
        for (int i = 1; i < 12; i++) {
            Invoice inv = new Invoice();
            inv.setPolicy(policy);
            BigDecimal amountToSet = (i == 11) ? lastInstallment : monthly;
            inv.setAmount(amountToSet.doubleValue());
            inv.setIssueDate(policy.getEffectiveDate().plusMonths(i));
            inv.setDueDate(inv.getIssueDate().plusDays(15));
            inv.setStatus("Open");
            invoiceList.add(inv);
        }
        invoiceRepository.saveAll(invoiceList);
    }

    // --- Standard Methods ---

    @Override
    public PolicyResponseDTO getById(Long policyID) {
        return policyRepository.findById(policyID)
                .map(policyMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Policy not found"));
    }

    @Override
    public List<PolicyResponseDTO> listByQuote(Long quoteID) {
        return policyRepository.findByQuote_QuoteID(quoteID).stream()
                .map(policyMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public PolicyResponseDTO getByPolicyNumber(String number) {
        return policyRepository.findByPolicyNumber(number)
                .map(policyMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Policy Number not found"));
    }

    @Override
    @Transactional
    public PolicyResponseDTO updateStatus(Long policyID, PolicyStatus status) {
        Policy p = policyRepository.findById(policyID)
                .orElseThrow(() -> new NotFoundException("Policy not found"));
        p.setStatus(status);

        return policyMapper.toResponse(policyRepository.save(p));
    }

    @Override
    @Transactional
    public PolicyResponseDTO bindQuoteToPolicy(PolicyRequestDTO req) {
        // Shifting logic to the new flow. For now, calling issuance directly to maintain compatibility
        issuePolicyFromQuote(req.getQuoteID());
        return getById(req.getQuoteID());
    }
}