package com.insuretrack.controller;

import com.insuretrack.dto.QuoteRequestDTO;
import com.insuretrack.dto.QuoteResponseDTO;
import com.insuretrack.entity.enums.QuoteStatus;
import com.insuretrack.service.QuoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for Quote lifecycle.
 * Base path: /api/quotes
 */
@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) { this.quoteService = quoteService; }

    @PostMapping
    public ResponseEntity<QuoteResponseDTO> create(@Valid @RequestBody QuoteRequestDTO req) {
        return ResponseEntity.ok(quoteService.createQuote(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponseDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(quoteService.getById(id));
    }

    @GetMapping("/customer/{customerID}")
    public ResponseEntity<List<QuoteResponseDTO>> byCustomer(@PathVariable Long customerID) {
        return ResponseEntity.ok(quoteService.listByCustomer(customerID));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<QuoteResponseDTO> updateStatus(@PathVariable Long id,
                                                         @RequestParam QuoteStatus status) {
        return ResponseEntity.ok(quoteService.updateStatus(id, status));
    }
}