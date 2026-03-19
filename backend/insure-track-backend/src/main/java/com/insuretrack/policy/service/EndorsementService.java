package com.insuretrack.policy.service;

import com.insuretrack.policy.dto.EndorsementRequestDTO;
import com.insuretrack.policy.dto.EndorsementResponseDTO;

public interface EndorsementService {
    EndorsementResponseDTO create(EndorsementRequestDTO requestDTO);
}
