package com.insuretrack.billing.service;

import com.insuretrack.billing.dto.RefundRequestDTO;
import com.insuretrack.billing.dto.RefundResponseDTO;

public interface RefundService {
    RefundResponseDTO initiateRefund(Long paymentId, RefundRequestDTO dto);
}
