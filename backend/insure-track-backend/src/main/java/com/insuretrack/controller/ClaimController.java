package com.insuretrack.controller;

import com.insuretrack.dto.*;
import com.insuretrack.entity.*;
import com.insuretrack.entity.enums.UserRole;
import com.insuretrack.repository.*;
import com.insuretrack.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping("/intake")
    public ResponseEntity<ClaimResponseDTO> intake(@RequestBody ClaimRequestDTO dto) {
        // Corrected method name to match Service: initiateClaim
        return ResponseEntity.ok(claimService.initiateClaim(dto));
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
    public ResponseEntity<String> addEvidence(
            @PathVariable Long claimID,
            @RequestBody EvidenceRequestDTO evidenceRequest) {
        claimService.addEvidence(claimID, evidenceRequest);
        return ResponseEntity.ok("Evidence added successfully");
    }
    @PostMapping("/{claimID}/settle")
    public ResponseEntity<SettlementResponseDTO> settleClaim(
            @PathVariable Long claimID) {
        // The amount and reference are handled inside the service via the Reserve logic
        return ResponseEntity.ok(claimService.settleClaim(claimID));
    }
    @GetMapping("/summary")
    public ResponseEntity<?> getClaimsSummary(@AuthenticationPrincipal User currentUser) {
        if (currentUser.getRole() == UserRole.ADMIN) {
            // Admin sees the global summary of all claims
            return ResponseEntity.ok(claimService.getGlobalSummary());
        } else if (currentUser.getRole() == UserRole.ADJUSTER) {
            // Adjuster sees their assigned queue
            return ResponseEntity.ok(claimService.getAssignedQueue(currentUser.getUserID()));
        } else {
            // Akhil only sees his specific claims
            return ResponseEntity.ok(claimService.listByCustomer(currentUser.getUserID()));
        }
    }
    @GetMapping("/my-queue")
    public ResponseEntity<List<ClaimResponseDTO>> getMyQueue(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String priority) {

        List<ClaimResponseDTO> queue = claimService.getAssignedQueue(currentUser.getUserID());

        if (priority != null && !priority.isEmpty()) {
            return ResponseEntity.ok(queue.stream()
                    .filter(c -> priority.equalsIgnoreCase(c.getPriority())) // Ensure your DTO has this field
                    .toList());
        }

        return ResponseEntity.ok(queue);
    }
    @PatchMapping("/{claimID}/priority")
    public ResponseEntity<String> escalateClaim(
            @PathVariable Long claimID,
            @RequestParam String priority) {
        claimService.updateClaimPriority(claimID, priority);
        return ResponseEntity.ok("Claim priority updated to " + priority);
    }

}