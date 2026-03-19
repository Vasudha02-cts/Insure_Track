package com.insuretrack.dto;

import lombok.Data;

@Data
public class CoverageRequestDTO {
    private Long productId;
    private String coverageType;
    private Double limit;
    private Double deductible;

}