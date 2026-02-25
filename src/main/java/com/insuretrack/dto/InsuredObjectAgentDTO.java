package com.insuretrack.dto;

import lombok.Data;

@Data
public class InsuredObjectAgentDTO {
    private Long customerID;
    private String detailsJSON;
    private String objectType;
    private Double valuation;
    private Double riskScore;
}