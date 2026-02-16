package com.insuretrack.service;

import com.insuretrack.dto.BeneficiaryRequestDTO;
import com.insuretrack.dto.BeneficiaryResponseDTO;
import java.util.List;

public interface BeneficiaryService {
    BeneficiaryResponseDTO updateBeneficiary(Long id, BeneficiaryRequestDTO dto);
    List<BeneficiaryResponseDTO> getBeneficiariesByCustomerId(Long customerId);
    void removeBeneficiary(Long id);
}