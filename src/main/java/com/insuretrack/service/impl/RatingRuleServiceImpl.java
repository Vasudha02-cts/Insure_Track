package com.insuretrack.service.impl;

import com.insuretrack.entity.RatingRule;
import com.insuretrack.entity.Product;
import com.insuretrack.repository.RatingRuleRepository;
import com.insuretrack.repository.ProductRepository;
import com.insuretrack.service.RatingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingRuleServiceImpl implements RatingRuleService {

    @Autowired private RatingRuleRepository ratingRuleRepository;
    @Autowired private ProductRepository productRepository;

    @Override
    public RatingRule addRule(Long productID, RatingRule rule) {
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        rule.setProduct(product);
        return ratingRuleRepository.save(rule);
    }

    @Override
    public List<RatingRule> getRulesByProduct(Long productID) {
        return ratingRuleRepository.findAll().stream()
                .filter(r -> r.getProduct().getProductID().equals(productID))
                .toList();
    }

    @Override
    public List<RatingRule> getAllRules() {
        return ratingRuleRepository.findAll();
    }

    @Override
    public RatingRule getRuleById(Long id) {
        return ratingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating Rule not found with ID: " + id));
    }

    @Override
    public RatingRule updateRule(Long id, RatingRule ruleDetails) {
        RatingRule rule = getRuleById(id);
        rule.setFactor(ruleDetails.getFactor());
        rule.setWeight(ruleDetails.getWeight());
        rule.setExpression(ruleDetails.getExpression());
        return ratingRuleRepository.save(rule);
    }

    @Override
    public void deleteRule(Long id) {
        ratingRuleRepository.deleteById(id);
    }
}