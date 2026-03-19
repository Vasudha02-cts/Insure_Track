package com.insuretrack.service;

import com.insuretrack.dto.CoverageRequestDTO;
import com.insuretrack.dto.CoverageResponseDTO;
import java.util.List;

public interface CoverageService {
    CoverageResponseDTO addCoverage(CoverageRequestDTO request);
    List<CoverageResponseDTO> getCoveragesByProduct(Long productId);
    void deleteCoverage(Long id);
    // Add to com.insuretrack.service.CoverageService
    List<CoverageResponseDTO> getAllCoverages();
    CoverageResponseDTO updateCoverage(Long id, CoverageRequestDTO request);
}