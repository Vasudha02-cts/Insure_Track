package com.insuretrack.controller;

import com.insuretrack.entity.RatingRule;
import com.insuretrack.service.RatingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating-rules")
public class RatingRuleController {

    @Autowired private RatingRuleService ratingRuleService;

    @PostMapping("/{productID}")
    public RatingRule createRule(@PathVariable Long productID, @RequestBody RatingRule rule) {
        return ratingRuleService.addRule(productID, rule);
    }

    @GetMapping("/product/{productID}")
    public List<RatingRule> getByProduct(@PathVariable Long productID) {
        return ratingRuleService.getRulesByProduct(productID);
    }

    @GetMapping
    public List<RatingRule> getAll() {
        return ratingRuleService.getAllRules();
    }

    @GetMapping("/{id}")
    public RatingRule getOne(@PathVariable Long id) {
        return ratingRuleService.getRuleById(id);
    }

    @PutMapping("/{id}")
    public RatingRule update(@PathVariable Long id, @RequestBody RatingRule rule) {
        return ratingRuleService.updateRule(id, rule);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        ratingRuleService.deleteRule(id);
        return "Rating Rule " + id + " deleted successfully";
    }
}