package com.insuretrack.service;

import com.insuretrack.dto.PolicyRequestDTO;
import com.insuretrack.dto.PolicyResponseDTO;
import com.insuretrack.entity.enums.PolicyStatus;
import java.util.List;

public interface PolicyService {
    // Step 1: Pre-bind logic (generates the first invoice for the quote)
    void prepareForBinding(Long quoteID);

    // Step 2: Final issuance (triggered by payment)
    void issuePolicyFromQuote(Long quoteID);

    // Standard lookups
    PolicyResponseDTO getById(Long policyID);
    List<PolicyResponseDTO> listByQuote(Long quoteID);
    PolicyResponseDTO getByPolicyNumber(String number);
    PolicyResponseDTO updateStatus(Long policyID, PolicyStatus status);

    // Kept for backward compatibility if needed, but logic shifts to issuePolicyFromQuote
    PolicyResponseDTO bindQuoteToPolicy(PolicyRequestDTO req);
}