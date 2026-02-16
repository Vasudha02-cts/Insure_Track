package com.insuretrack.controller;

import com.insuretrack.dto.*;
import com.insuretrack.entity.*;
import com.insuretrack.repository.*;
import com.insuretrack.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping("/intake")
    public ResponseEntity<ClaimResponseDTO> intake(@RequestBody ClaimRequestDTO dto) {
        return ResponseEntity.ok(claimService.processIntake(dto));
    }


    @PostMapping("/{claimID}/assign")
    public ResponseEntity<ClaimAssignmentResponseDTO> assign(
            @PathVariable Long claimID,
            @RequestParam Long adjusterId,
            @RequestParam String priority) {
        return ResponseEntity.ok(claimService.assignAdjuster(claimID, adjusterId, priority));
    }

    @PostMapping("/{claimID}/reserve")
    public ResponseEntity<ReserveResponseDTO> setReserve(
            @PathVariable Long claimID,
            @RequestParam Long adjusterId,
            @RequestParam Double amount) {

        // The service returns ReserveResponseDTO, so the ResponseEntity must match
        return ResponseEntity.ok(claimService.updateReserves(claimID, adjusterId, amount));
    }

    @PostMapping("/{claimID}/evidence")
    public ResponseEntity<EvidenceResponseDTO> addEvidence(
            @PathVariable Long claimID,
            @RequestBody EvidenceRequestDTO evidenceRequest) {
        return ResponseEntity.ok(claimService.addEvidence(claimID, evidenceRequest));
    }

    @PostMapping("/{claimID}/settle")
    public ResponseEntity<SettlementResponseDTO> settleClaim(
            @PathVariable Long claimID,
            @RequestParam Double amount,
            @RequestParam String paymentReference) {
        return ResponseEntity.ok(claimService.settleClaim(claimID, amount, paymentReference));
    }
    @GetMapping("/{claimID}/summary")
    public ResponseEntity<ClaimSummaryDTO> getClaimSummary(@PathVariable Long claimID) {
        // This now returns a clean DTO instead of a Map
        ClaimSummaryDTO summary = claimService.getClaimSummary(claimID);
        return ResponseEntity.ok(summary);
    }


}