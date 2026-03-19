package com.insuretrack.quote.controller;

import com.insuretrack.policy.entity.Policy;
import com.insuretrack.quote.dto.QuoteRequestDTO;
import com.insuretrack.quote.dto.QuoteResponseDTO;
import com.insuretrack.quote.service.QuoteService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent/quotes")
@AllArgsConstructor
public class QuoteController {


    private final QuoteService quoteService;

    @PostMapping("/draft")
    public ResponseEntity<QuoteResponseDTO> createDraft(
            @RequestBody QuoteRequestDTO request) {

        return ResponseEntity.ok(
                quoteService.createQuote(request)
        );
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<QuoteResponseDTO> submitQuote(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                quoteService.submitQuote(id)
        );
    }

    @PutMapping("/{id}/rate")
    public ResponseEntity<QuoteResponseDTO> rateQuote(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                quoteService.rateQuote(id)
        );
    }
}