package com.insuretrack.repository;

import com.insuretrack.entity.Coverage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CoverageRepository extends JpaRepository<Coverage, Long> {
    // Find all coverages for a specific product
    List<Coverage> findByProduct_ProductID(Long productID);
}