package com.insuretrack.controller;

import com.insuretrack.dto.RatingRuleRequestDTO;
import com.insuretrack.dto.RatingRuleResponseDTO;
import com.insuretrack.service.RatingRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rating-rules")
@RequiredArgsConstructor
public class RatingController {

    private final RatingRuleService ratingRuleService;

    @PostMapping
    public ResponseEntity<RatingRuleResponseDTO> create(@RequestBody RatingRuleRequestDTO dto) {
        return ResponseEntity.ok(ratingRuleService.addRule(dto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<RatingRuleResponseDTO>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(ratingRuleService.getRulesByProduct(productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ratingRuleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}