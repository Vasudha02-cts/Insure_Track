package com.insuretrack.service.impl;

import com.insuretrack.entity.Coverage;
import com.insuretrack.entity.Product;
import com.insuretrack.repository.CoverageRepository;
import com.insuretrack.repository.ProductRepository;
import com.insuretrack.service.CoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoverageServiceImpl implements CoverageService {

    @Autowired private CoverageRepository coverageRepository;
    @Autowired private ProductRepository productRepository;

    @Override
    public Coverage addCoverage(Long productID, Coverage coverage) {
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        coverage.setProduct(product); // Linking the coverage to our product
        return coverageRepository.save(coverage);
    }

    @Override
    public List<Coverage> getCoveragesByProduct(Long productID) {
        return coverageRepository.findByProduct_ProductID(productID);
    }

    @Override
    public void deleteCoverage(Long id) {
        coverageRepository.deleteById(id);
    }
    @Override
    public List<Coverage> getAllCoverages() {
        return coverageRepository.findAll();
    }

    @Override
    public Coverage updateCoverage(Long id, Coverage coverageDetails) {
        // Find the existing coverage by its OWN ID, not the Product ID
        Coverage coverage = coverageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coverage not found with id: " + id));

        // Update the allowed fields
        coverage.setCoverageType(coverageDetails.getCoverageType());
        coverage.setLimit(coverageDetails.getLimit());
        coverage.setDeductible(coverageDetails.getDeductible());

        return coverageRepository.save(coverage);
    }
}