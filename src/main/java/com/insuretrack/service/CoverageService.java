package com.insuretrack.service;

import com.insuretrack.entity.Coverage;

import java.util.List;

public interface CoverageService {
    Coverage addCoverage(Long productID, Coverage coverage);
    List<Coverage> getCoveragesByProduct(Long productID);
    void deleteCoverage(Long id);

    // Add these:
    Coverage updateCoverage(Long id, Coverage coverage); // To modify limits/deductibles
    List<Coverage> getAllCoverages();                   // To see everything at once
}