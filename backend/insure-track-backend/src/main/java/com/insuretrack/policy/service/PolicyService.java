package com.insuretrack.policy.service;

import com.insuretrack.policy.dto.PolicyResponseDTO;

public interface PolicyService {
    PolicyResponseDTO issuePolicy(Long quoteId);
    PolicyResponseDTO getPolicy(Long policyId);
}
