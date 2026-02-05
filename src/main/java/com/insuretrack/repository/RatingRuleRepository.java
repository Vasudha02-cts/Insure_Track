package com.insuretrack.repository;

import com.insuretrack.entity.RatingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRuleRepository extends JpaRepository<RatingRule, Long> {}
