package com.insuretrack.service;

import com.insuretrack.dto.CreateRenewalOfferRequest;
import com.insuretrack.dto.RenewalRequestDTO;
import com.insuretrack.dto.RenewalResponseDTO;
import java.util.List;

public interface RenewalService {
    RenewalResponseDTO offer(CreateRenewalOfferRequest req);
    RenewalResponseDTO decide(RenewalRequestDTO req);
    List<RenewalResponseDTO> listByPolicy(Long policyID);
}