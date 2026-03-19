package com.insuretrack.service;

import com.insuretrack.dto.*;
import java.util.List;

public interface ClaimService {
    // Basic Flow
    ClaimResponseDTO initiateClaim(ClaimRequestDTO request);

    // Assignment and Triage
    ClaimAssignmentResponseDTO assignAdjuster(Long claimID, Long adjusterId, String priority);
    void updateClaimPriority(Long claimID, String priority);

    // Financials
    ReserveResponseDTO setClaimReserve(Long claimID, Double estimatedRepairCost);
    // Overloaded or renamed for the controller's specific call
    ReserveResponseDTO updateReserves(Long claimID, Long adjusterId, Double amount);
    SettlementResponseDTO settleClaim(Long claimID);

    // Evidence (Changed from void to DTO to fix 'void type not allowed')
    EvidenceResponseDTO addEvidence(Long claimID, EvidenceRequestDTO evidence);

    // Summary and Queues
    Object getGlobalSummary(); // Or a specific Summary DTO
    List<ClaimResponseDTO> getAssignedQueue(Long adjusterId);
    List<ClaimResponseDTO> listByCustomer(Long customerId);
}