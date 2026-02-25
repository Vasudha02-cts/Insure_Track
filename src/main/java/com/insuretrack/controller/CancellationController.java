package com.insuretrack.controller;

import com.insuretrack.dto.CancellationRequestDTO;
import com.insuretrack.dto.CancellationResponseDTO;
import com.insuretrack.service.CancellationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for Cancellations.
 * Base path: /api/cancellations
 */
@RestController
@RequestMapping("/api/cancellations")
public class CancellationController {

    private final CancellationService cancellationService;

    public CancellationController(CancellationService cancellationService) {
        this.cancellationService = cancellationService;
    }

    @PostMapping
    public ResponseEntity<CancellationResponseDTO> request(@Valid @RequestBody CancellationRequestDTO req) {
        return ResponseEntity.ok(cancellationService.requestCancellation(req));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<CancellationResponseDTO> approve(@PathVariable Long id) {
        return ResponseEntity.ok(cancellationService.approve(id));
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<CancellationResponseDTO> process(@PathVariable Long id) {
        return ResponseEntity.ok(cancellationService.process(id));
    }

    @GetMapping("/policy/{policyID}")
    public ResponseEntity<List<CancellationResponseDTO>> byPolicy(@PathVariable Long policyID) {
        return ResponseEntity.ok(cancellationService.listByPolicy(policyID));
    }
}