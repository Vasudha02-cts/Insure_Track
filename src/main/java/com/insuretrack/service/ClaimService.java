package com.insuretrack.service;

import com.insuretrack.dto.*;
import com.insuretrack.entity.*;
import java.util.Map;

public interface ClaimService {
    // Return DTO instead of raw Claim entity [cite: 5, 23]
    ClaimResponseDTO processIntake(ClaimRequestDTO dto);

    ClaimAssignmentResponseDTO assignAdjuster(Long claimID, Long adjusterID, String priority);

    ReserveResponseDTO updateReserves(Long claimID, Long adjusterID, Double amount);
    EvidenceResponseDTO addEvidence(Long claimID, EvidenceRequestDTO evidenceRequest);
    SettlementResponseDTO settleClaim(Long claimID, Double amount, String paymentReference);
    ClaimSummaryDTO getClaimSummary(Long claimID);
}