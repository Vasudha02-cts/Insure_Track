package com.insuretrack.service;

import com.insuretrack.dto.EndorsementRequestDTO;
import com.insuretrack.dto.EndorsementResponseDTO;
import java.util.List;

public interface EndorsementService {
    EndorsementResponseDTO createDraft(EndorsementRequestDTO req);
    EndorsementResponseDTO approve(Long endorsementID);
    EndorsementResponseDTO apply(Long endorsementID);
    List<EndorsementResponseDTO> listByPolicy(Long policyID);
}