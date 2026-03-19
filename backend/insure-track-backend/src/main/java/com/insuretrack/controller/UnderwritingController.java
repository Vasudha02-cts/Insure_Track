package com.insuretrack.controller;

import com.insuretrack.dto.UnderwritingRequestDTO;
import com.insuretrack.dto.UnderwritingResponseDTO;
import com.insuretrack.service.UnderwritingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/underwriter")
@RequiredArgsConstructor
public class UnderwritingController {

    private final UnderwritingService underwritingService;

    @PostMapping("/review")
    public ResponseEntity<UnderwritingResponseDTO> reviewQuote(@Valid @RequestBody UnderwritingRequestDTO request) {
        return ResponseEntity.ok(underwritingService.reviewQuote(request));
    }

    @GetMapping("/quote/{quoteId}")
    public ResponseEntity<UnderwritingResponseDTO> getStatus(@PathVariable Long quoteId) {
        return ResponseEntity.ok(underwritingService.getHistoryByQuote(quoteId));
    }
}