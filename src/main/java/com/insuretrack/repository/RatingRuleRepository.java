package com.insuretrack.repository;

import com.insuretrack.entity.RatingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRuleRepository extends JpaRepository<RatingRule, Long> {
    List<RatingRule> findByProduct_ProductID(Long productID);
}
