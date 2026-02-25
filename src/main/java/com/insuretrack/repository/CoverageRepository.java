package com.insuretrack.repository;

import com.insuretrack.entity.Coverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoverageRepository extends JpaRepository<Coverage, Long> {
    // Allows us to find all coverages associated with a specific Product
    List<Coverage> findByProduct_ProductID(Long productID);
}