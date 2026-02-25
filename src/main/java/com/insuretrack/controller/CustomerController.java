package com.insuretrack.controller;

import com.insuretrack.dto.QuoteRequestDTO;
import com.insuretrack.dto.QuoteResponseDTO;
import com.insuretrack.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final QuoteService quoteService;

    @PostMapping("/quotes/select-coverages")
    public ResponseEntity<QuoteResponseDTO> initiateQuote(@RequestBody QuoteRequestDTO dto) {
        // Logic will check if InsuredObject is ACTIVE before proceeding
        return ResponseEntity.ok(quoteService.createQuote(dto));
    }
}