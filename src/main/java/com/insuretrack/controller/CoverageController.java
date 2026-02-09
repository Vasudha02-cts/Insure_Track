package com.insuretrack.controller;

import com.insuretrack.entity.Coverage;
import com.insuretrack.service.CoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/coverages")
public class CoverageController {

    @Autowired private CoverageService coverageService;

    @PostMapping("/{productID}")
    public Coverage addCoverage(@PathVariable Long productID, @RequestBody Coverage coverage) {
        return coverageService.addCoverage(productID, coverage);
    }

    // Get all coverages across all products
    @GetMapping
    public List<Coverage> getAll() {
        return coverageService.getAllCoverages();
    }

    // Update a specific coverage (Fixes that 405 error you saw)
    @PutMapping("/{id}")
    public Coverage updateCoverage(@PathVariable Long id, @RequestBody Coverage coverage) {
        return coverageService.updateCoverage(id, coverage);
    }

    // Delete a coverage
    @DeleteMapping("/{id}")
    public String deleteCoverage(@PathVariable Long id) {
        coverageService.deleteCoverage(id);
        return "Coverage deleted successfully";
    }
    // New methods to add:




}