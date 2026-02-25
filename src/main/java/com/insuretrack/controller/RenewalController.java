package com.insuretrack.controller;

import com.insuretrack.dto.CreateRenewalOfferRequest;
import com.insuretrack.dto.RenewalRequestDTO;
import com.insuretrack.dto.RenewalResponseDTO;
import com.insuretrack.service.RenewalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for Renewals.
 * Base path: /api/renewals
 */
@RestController
@RequestMapping("/api/renewals")
public class RenewalController {

    private final RenewalService renewalService;

    public RenewalController(RenewalService renewalService) { this.renewalService = renewalService; }

    @PostMapping("/offer")
    public ResponseEntity<RenewalResponseDTO> offer(@Valid @RequestBody CreateRenewalOfferRequest req) {
        return ResponseEntity.ok(renewalService.offer(req));
    }

    @PostMapping("/decide")
    public ResponseEntity<RenewalResponseDTO> decide(@Valid @RequestBody RenewalRequestDTO req) {
        return ResponseEntity.ok(renewalService.decide(req));
    }

    @GetMapping("/policy/{policyID}")
    public ResponseEntity<List<RenewalResponseDTO>> byPolicy(@PathVariable Long policyID) {
        return ResponseEntity.ok(renewalService.listByPolicy(policyID));
    }
}