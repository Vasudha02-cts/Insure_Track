package com.insuretrack.service;

import com.insuretrack.entity.Policy;
import com.insuretrack.entity.Quote;

public interface RatingEngineService {
    /**
     * Calculates premium based on formula:
     * (basePay * weight) + (riskScore * 10)
     * where basePay = sum of limits and deductibles of selected coverages.
     */
    double calculatePremium(Quote quote);
    Double calculateEndorsementDelta(Policy policy, Double rawCost);
}