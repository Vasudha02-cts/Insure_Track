package com.insuretrack.controller;

import com.insuretrack.dto.PolicyRequestDTO;
import com.insuretrack.dto.PolicyResponseDTO;
import com.insuretrack.entity.enums.PolicyStatus;
import com.insuretrack.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for Policy operations.
 * Base path: /api/policies
 */
@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) { this.policyService = policyService; }

    @PostMapping("/bind")
    public ResponseEntity<PolicyResponseDTO> bindPolicy(@RequestBody PolicyRequestDTO dto) {
        // Only needs quoteID and effectiveDate
        return ResponseEntity.ok(policyService.bindQuoteToPolicy(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(policyService.getById(id));
    }

    @GetMapping("/quote/{quoteID}")
    public ResponseEntity<List<PolicyResponseDTO>> byQuote(@PathVariable Long quoteID) {
        return ResponseEntity.ok(policyService.listByQuote(quoteID));
    }

    @GetMapping("/number/{policyNumber}")
    public ResponseEntity<PolicyResponseDTO> byNumber(@PathVariable String policyNumber) {
        return ResponseEntity.ok(policyService.getByPolicyNumber(policyNumber));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PolicyResponseDTO> updateStatus(@PathVariable Long id,
                                                          @RequestParam PolicyStatus status) {
        return ResponseEntity.ok(policyService.updateStatus(id, status));
    }
}