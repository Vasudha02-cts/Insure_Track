package com.insuretrack.service.impl;

import com.insuretrack.dto.RatingRuleRequestDTO;
import com.insuretrack.dto.RatingRuleResponseDTO;
import com.insuretrack.entity.RatingRule;
import com.insuretrack.entity.Product;
import com.insuretrack.mapper.RatingMapper;
import com.insuretrack.repository.RatingRuleRepository;
import com.insuretrack.repository.ProductRepository;
import com.insuretrack.service.RatingRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingRuleServiceImpl implements RatingRuleService {

    private final RatingRuleRepository ratingRuleRepository;
    private final ProductRepository productRepository;
    private final RatingMapper ratingRuleMapper;

    @Override
    @Transactional
    public RatingRuleResponseDTO addRule(RatingRuleRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        RatingRule rule = ratingRuleMapper.toEntity(dto);
        rule.setProduct(product);

        // FIX: Change toResponse() to match your RatingMapper interface method
        return ratingRuleMapper.toResponse(ratingRuleRepository.save(rule));
    }

    @Override
    public List<RatingRuleResponseDTO> getRulesByProduct(Long productId) {
        return ratingRuleRepository.findByProduct_ProductID(productId).stream()
                .map(ratingRuleMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteRule(Long ruleId) {
        ratingRuleRepository.deleteById(ruleId);
    }
}