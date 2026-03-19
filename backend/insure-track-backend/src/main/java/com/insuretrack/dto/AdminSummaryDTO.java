package com.insuretrack.dto;

import lombok.Data;

@Data
public class AdminSummaryDTO {
    private long totalPolicies;
    private Double totalRevenue;
    private long openClaimsCount;
    private long urgentClaimsCount;
}