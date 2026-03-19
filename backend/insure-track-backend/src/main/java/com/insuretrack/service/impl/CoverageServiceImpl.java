package com.insuretrack.service.impl;

import com.insuretrack.dto.CoverageRequestDTO;
import com.insuretrack.dto.CoverageResponseDTO;
import com.insuretrack.entity.Coverage;
import com.insuretrack.entity.Product;
import com.insuretrack.mapper.CoverageMapper;
import com.insuretrack.repository.CoverageRepository;
import com.insuretrack.repository.ProductRepository;
import com.insuretrack.service.CoverageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoverageServiceImpl implements CoverageService {

    private final CoverageRepository coverageRepository;
    private final CoverageMapper coverageMapper;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public CoverageResponseDTO addCoverage(CoverageRequestDTO request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Coverage coverage = coverageMapper.toEntity(request);
        coverage.setProduct(product);
        return coverageMapper.toDto(coverageRepository.save(coverage));
    }

    @Override
    public List<CoverageResponseDTO> getCoveragesByProduct(Long productId) {
        // FIX: Update the method call here as well
        return coverageRepository.findByProduct_ProductID(productId).stream()
                .map(coverageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCoverage(Long id) {
        if (!coverageRepository.existsById(id)) {
            throw new EntityNotFoundException("Coverage not found");
        }
        coverageRepository.deleteById(id);
    }
    // Add to com.insuretrack.service.impl.CoverageServiceImpl

    @Override
    public List<CoverageResponseDTO> getAllCoverages() {
        return coverageRepository.findAll().stream()
                .map(coverageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CoverageResponseDTO updateCoverage(Long id, CoverageRequestDTO request) {
        // Check if the coverage exists
        Coverage existing = coverageRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Coverage not found with ID: " + id));

        // Map new values from Request DTO to Entity
        existing.setCoverageType(request.getCoverageType());
        existing.setLimitAmount(request.getLimit());
        existing.setDeductible(request.getDeductible());
        // 5. FIX: Update the product relationship if the ID changed
        if (!existing.getProduct().getProductID().equals(request.getProductId())) {
            Product newProduct = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("New Product not found"));
            existing.setProduct(newProduct);
        }

        // Save updated entity and return as Response DTO
        return coverageMapper.toDto(coverageRepository.save(existing));
    }
}