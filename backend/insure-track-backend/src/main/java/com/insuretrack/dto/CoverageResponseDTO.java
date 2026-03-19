package com.insuretrack.dto;

import lombok.Data;

@Data
public class CoverageResponseDTO {
    private Long coverageID;
    private Long productID;

    private String productName; // Helpful for the UI
    private String coverageType;
    private Double limit;
    private Double deductible;
}