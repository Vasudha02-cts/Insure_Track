package com.insuretrack.service.impl;

import com.insuretrack.dto.RefundRequestDTO;
import com.insuretrack.dto.RefundResponseDTO;
import com.insuretrack.entity.Payment;
import com.insuretrack.entity.Refund;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.RefundMapper;
import com.insuretrack.repository.PaymentRepository;
import com.insuretrack.repository.RefundRepository;
import com.insuretrack.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;
    private final RefundMapper refundMapper;

    @Override
    @Transactional
    public RefundResponseDTO createRefund(RefundRequestDTO dto) {
        // 1. Validate Payment
        Payment payment = paymentRepository.findById(dto.getPaymentId())
                .orElseThrow(() -> new NotFoundException("Payment record not found: " + dto.getPaymentId()));

        // 2. Business Validation
        validateRefundAmount(payment, dto.getAmount());

        // 3. Map and Link
        Refund refund = refundMapper.toEntity(dto);
        refund.setPayment(payment);

        // Ensure status and date are set if not in DTO
        if (refund.getStatus() == null) refund.setStatus("INITIATED");
        refund.setProcessedDate(LocalDate.now());

        Refund savedRefund = refundRepository.save(refund);
        return refundMapper.toResponse(savedRefund);
    }

    /**
     * Logic for automatic refunds triggered by Policy Cancellation
     */
    @Transactional
    public RefundResponseDTO processCancellationRefund(Long paymentId, Double calculatedAmount, String reason) {
        RefundRequestDTO dto = new RefundRequestDTO();
        dto.setPaymentId(paymentId);
        dto.setAmount(calculatedAmount);
        dto.setReason("Policy Cancellation: " + reason);

        return createRefund(dto);
    }

    private void validateRefundAmount(Payment payment, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Refund amount must be positive.");
        }

        double totalRefunded = refundRepository.findByPayment_PaymentID(payment.getPaymentID())
                .stream()
                .filter(r -> !"FAILED".equalsIgnoreCase(r.getStatus()))
                .mapToDouble(Refund::getAmount)
                .sum();

        if (totalRefunded + amount > payment.getAmount()) {
            throw new IllegalStateException("Exceeds original payment of " + payment.getAmount());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RefundResponseDTO getRefundById(Long id) {
        return refundRepository.findById(id)
                .map(refundMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Refund not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RefundResponseDTO> getAllRefunds() {
        return refundRepository.findAll().stream()
                .map(refundMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RefundResponseDTO deleteRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Refund not found"));
        RefundResponseDTO response = refundMapper.toResponse(refund);
        refundRepository.delete(refund);
        return response;
    }
}