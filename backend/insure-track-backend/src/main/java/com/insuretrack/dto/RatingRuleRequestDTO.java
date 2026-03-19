package com.insuretrack.dto;

import lombok.Data;

// Example of the Rating Rule DTO
@Data
public class RatingRuleRequestDTO {
    private Long productID;
    private String factor; // e.g., "VehicleType"
    private Double weight; // e.g., 1.2
    private String expression; // "(limit + deductible) * factor + riskscore * 10"
}