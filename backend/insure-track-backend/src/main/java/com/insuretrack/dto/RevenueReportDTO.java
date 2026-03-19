package com.insuretrack.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RevenueReportDTO {
    private Double totalPremiumCollected;
    private Integer activePolicyCount;
    private Double totalClaimsPaid;
    private Map<String, Long> policiesByStatus;
}