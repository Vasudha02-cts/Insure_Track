package com.insuretrack.dto;

import lombok.Data;

@Data
public class RatingRuleResponseDTO {
    private Long ruleID;
    private Long productID;
    private String productName;
    private String factor;
    private Double weight;
    private String expression;
}