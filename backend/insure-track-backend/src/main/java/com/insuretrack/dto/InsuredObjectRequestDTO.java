package com.insuretrack.dto;

import lombok.Data;

@Data
public class InsuredObjectRequestDTO {
    private String objectType;
    private String detailsJSON;
    private Double valuation;
    private Double riskScore;
    private String status;
}
