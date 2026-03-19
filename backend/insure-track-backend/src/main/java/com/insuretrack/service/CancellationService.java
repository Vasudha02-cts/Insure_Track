package com.insuretrack.service;

import com.insuretrack.dto.CancellationRequestDTO;
import com.insuretrack.dto.CancellationResponseDTO;
import java.util.List;

public interface CancellationService {
    CancellationResponseDTO requestCancellation(CancellationRequestDTO req);
    CancellationResponseDTO approve(Long cancellationID);
    CancellationResponseDTO process(Long cancellationID);
    List<CancellationResponseDTO> listByPolicy(Long policyID);
}