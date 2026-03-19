package com.insuretrack.service;

import com.insuretrack.dto.RatingRuleRequestDTO;
import com.insuretrack.dto.RatingRuleResponseDTO;
import java.util.List;

public interface RatingRuleService {
    RatingRuleResponseDTO addRule(RatingRuleRequestDTO dto);
    List<RatingRuleResponseDTO> getRulesByProduct(Long productId);
    void deleteRule(Long ruleId);
}