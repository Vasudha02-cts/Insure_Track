package com.insuretrack.service.impl;

import com.insuretrack.dto.CancellationRequestDTO;
import com.insuretrack.dto.CancellationResponseDTO;
import com.insuretrack.entity.*;
import com.insuretrack.entity.enums.CancellationStatus;
import com.insuretrack.entity.enums.PolicyStatus;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.CancellationMapper;
import com.insuretrack.repository.*;
import com.insuretrack.service.CancellationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CancellationServiceImpl implements CancellationService {

    private final CancellationRepository cancellationRepository;
    private final PolicyRepository policyRepository;
    private final InvoiceRepository invoiceRepository;
    private final CancellationMapper cancellationMapper;

    @Override
    public CancellationResponseDTO requestCancellation(CancellationRequestDTO req) {
        Policy policy = policyRepository.findById(req.getPolicyID())
                .orElseThrow(() -> new NotFoundException("Policy not found: " + req.getPolicyID()));

        if (policy.getStatus() == PolicyStatus.Cancelled) {
            throw new IllegalStateException("Policy is already cancelled.");
        }

        Cancellation c = cancellationMapper.toEntity(req);
        c.setPolicyID(policy.getPolicyID());
        c.setStatus(CancellationStatus.Requested);
        c.setEffectiveDate(LocalDate.now());

        // FIX: Set a default value so the NotNull constraint doesn't fail
        c.setRefundAmount(BigDecimal.ZERO);

        return cancellationMapper.toResponse(cancellationRepository.save(c));
    }

    @Override
    public CancellationResponseDTO approve(Long cancellationID) {
        Cancellation c = cancellationRepository.findById(cancellationID)
                .orElseThrow(() -> new NotFoundException("Cancellation request not found"));
        c.setStatus(CancellationStatus.Approved);
        return cancellationMapper.toResponse(cancellationRepository.save(c));
    }

    @Override
    @Transactional
    public CancellationResponseDTO process(Long cancellationID) {
        Cancellation c = cancellationRepository.findById(cancellationID)
                .orElseThrow(() -> new NotFoundException("Cancellation record not found"));

        if (c.getStatus() != CancellationStatus.Approved) {
            throw new IllegalStateException("Cancellation must be Approved before processing.");
        }

        Policy p = policyRepository.findById(c.getPolicyID())
                .orElseThrow(() -> new NotFoundException("Policy not found"));

        // 1. PRO-RATA REFUND MATH
        // Refund = Total Premium * (Remaining Days / 365)
        long totalDays = 365;
        long daysUsed = ChronoUnit.DAYS.between(p.getEffectiveDate(), LocalDate.now());
        long remainingDays = Math.max(0, totalDays - daysUsed);

        double dailyRate = p.getPremium().doubleValue() / totalDays;
        double refundValue = dailyRate * remainingDays;
        c.setRefundAmount(BigDecimal.valueOf(refundValue));

        // 2. VOID FUTURE INVOICES
        List<Invoice> openInvoices = invoiceRepository.findByPolicy_PolicyID(p.getPolicyID())
                .stream()
                .filter(inv -> "Open".equalsIgnoreCase(inv.getStatus()))
                .collect(Collectors.toList());

        for (Invoice inv : openInvoices) {
            inv.setStatus("Voided");
        }
        invoiceRepository.saveAll(openInvoices);

        // 3. UPDATE STATUSES
        p.setStatus(PolicyStatus.Cancelled);
        policyRepository.save(p);

        c.setStatus(CancellationStatus.Processed);
        return cancellationMapper.toResponse(cancellationRepository.save(c));
    }

    @Override
    public List<CancellationResponseDTO> listByPolicy(Long policyID) {
        return cancellationRepository.findByPolicy_PolicyID(policyID)
                .stream()
                .map(cancellationMapper::toResponse)
                .collect(Collectors.toList());
    }
}