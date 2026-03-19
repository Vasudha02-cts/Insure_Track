package com.insuretrack.controller;

import com.insuretrack.dto.CoverageRequestDTO;
import com.insuretrack.dto.CoverageResponseDTO;
import com.insuretrack.service.CoverageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/coverages")
@RequiredArgsConstructor
@Tag(name = "Coverage Management")
public class CoverageController {

    private final CoverageService coverageService;

    @PostMapping
    public ResponseEntity<CoverageResponseDTO> create(@RequestBody CoverageRequestDTO request) {
        return ResponseEntity.ok(coverageService.addCoverage(request));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CoverageResponseDTO>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(coverageService.getCoveragesByProduct(productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coverageService.deleteCoverage(id);
        return ResponseEntity.noContent().build();
    }
    // Add to com.insuretrack.controller.CoverageController

    @GetMapping
    @io.swagger.v3.oas.annotations.Operation(summary = "Get all coverages in the system")
    public ResponseEntity<List<CoverageResponseDTO>> getAll() {
        return ResponseEntity.ok(coverageService.getAllCoverages());
    }

    @PutMapping("/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Update an existing coverage")
    public ResponseEntity<CoverageResponseDTO> update(@PathVariable Long id, @RequestBody CoverageRequestDTO request) {
        return ResponseEntity.ok(coverageService.updateCoverage(id, request));
    }
}