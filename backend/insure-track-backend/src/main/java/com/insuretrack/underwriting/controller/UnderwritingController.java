package com.insuretrack.underwriting.controller;

import com.insuretrack.underwriting.dto.UnderwritingDecisionDTO;
import com.insuretrack.underwriting.dto.UnderwritingRequestDTO;
import com.insuretrack.underwriting.dto.UnderwritingResponseDTO;
import com.insuretrack.underwriting.service.UnderwritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/underwriter")
@RequiredArgsConstructor
public class UnderwritingController {

    private final UnderwritingService underwritingService;

    // Create case
    @PostMapping("/{quoteId}")
    public UnderwritingResponseDTO createCase(
            @PathVariable Long quoteId) {

        return underwritingService.createCase(quoteId);
    }

    // Get pending cases
    @GetMapping("/pending")
    public
    List<UnderwritingResponseDTO> getPending() {
        return underwritingService.getPendingCases();
    }
    
    @PutMapping("/{caseId}/decision")
    public ResponseEntity<UnderwritingResponseDTO> decide(@PathVariable Long caseId,@RequestBody UnderwritingDecisionDTO request){
        return ResponseEntity.ok(underwritingService.makeDecision(caseId,request));
    }
}
