package com.insuretrack.repository;

import com.insuretrack.entity.Coverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverageRepository extends JpaRepository<Coverage, Long> {}
