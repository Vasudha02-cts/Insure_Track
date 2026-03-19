package com.insuretrack.service;

import com.insuretrack.dto.InsuredObjectAgentDTO;
import com.insuretrack.dto.InsuredObjectRequestDTO;
import com.insuretrack.dto.InsuredObjectResponseDTO;
import java.util.List;

public interface InsuredObjectService {
    InsuredObjectResponseDTO updateObject(Long objectId, InsuredObjectRequestDTO dto);
    InsuredObjectResponseDTO getObjectById(Long objectId);
    List<InsuredObjectResponseDTO> getObjectsByCustomerId(Long customerId);
    void deleteObject(Long objectId);
    InsuredObjectResponseDTO agentCreateObject(InsuredObjectAgentDTO dto);
}