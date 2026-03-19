package com.insuretrack.controller;

import com.insuretrack.dto.EndorsementRequestDTO;
import com.insuretrack.dto.EndorsementResponseDTO;
import com.insuretrack.service.EndorsementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for Endorsements.
 * Base path: /api/endorsements
 */
@RestController
@RequestMapping("/api/endorsements")
public class EndorsementController {

    private final EndorsementService endorsementService;

    public EndorsementController(EndorsementService endorsementService) {
        this.endorsementService = endorsementService;
    }

    @PostMapping
    public ResponseEntity<EndorsementResponseDTO> create(@Valid @RequestBody EndorsementRequestDTO req) {
        return ResponseEntity.ok(endorsementService.createDraft(req));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<EndorsementResponseDTO> approve(@PathVariable Long id) {
        return ResponseEntity.ok(endorsementService.approve(id));
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<EndorsementResponseDTO> apply(@PathVariable Long id) {
        return ResponseEntity.ok(endorsementService.apply(id));
    }

    @GetMapping("/policy/{policyID}")
    public ResponseEntity<List<EndorsementResponseDTO>> byPolicy(@PathVariable Long policyID) {
        return ResponseEntity.ok(endorsementService.listByPolicy(policyID));
    }
}