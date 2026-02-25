package com.insuretrack.service;

import com.insuretrack.dto.RefundRequestDTO;
import com.insuretrack.dto.RefundResponseDTO;
import java.util.List;

public interface RefundService {
    RefundResponseDTO createRefund(RefundRequestDTO dto);
    RefundResponseDTO getRefundById(Long id);
    List<RefundResponseDTO> getAllRefunds();
    RefundResponseDTO deleteRefund(Long id);
}