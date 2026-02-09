package com.insuretrack.service;

import com.insuretrack.entity.RatingRule;
import java.util.List;

public interface RatingRuleService {
    RatingRule addRule(Long productID, RatingRule rule);
    List<RatingRule> getRulesByProduct(Long productID);
    List<RatingRule> getAllRules();
    RatingRule getRuleById(Long id);
    RatingRule updateRule(Long id, RatingRule ruleDetails);
    void deleteRule(Long id);
}