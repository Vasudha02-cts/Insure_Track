package com.insuretrack.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insuretrack.entity.Policy;
import com.insuretrack.entity.Quote;
import com.insuretrack.service.RatingEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper; // <--- This one
@Service
@RequiredArgsConstructor
public class RatingEngineServiceImpl implements RatingEngineService {

    private final ObjectMapper objectMapper; // Spring injects this automatically

    @Override
    public double calculatePremium(Quote quote) {
        // 1. Get the age multiplier we built earlier
        int age = calculateVehicleAge(quote.getInsuredObject().getDetailsJSON());
        double riskMultiplier = getAgeMultiplier(age);

        // 2. Parse selected coverages and sum base prices
        double baseCoverageSum = 0.0;
        String coverages = quote.getCoveragesJSON().toLowerCase();

        if (coverages.contains("collision")) baseCoverageSum += 300.0;
        if (coverages.contains("liability")) baseCoverageSum += 150.0;
        if (coverages.contains("fire")) baseCoverageSum += 100.0;

        // 3. Final Math: (Base Sum * Risk Multiplier)
        System.out.println("The premium is "+baseCoverageSum * riskMultiplier);
        return baseCoverageSum * riskMultiplier;
    }

    /**
     * Extracts the 'year' from the detailsJSON and compares it to current year.
     */
    private int calculateVehicleAge(String detailsJson) {
        try {
            // Parses the JSON string: {"make":"Tesla","year":2020...}
            JsonNode node = objectMapper.readTree(detailsJson);
            int manufactureYear = node.get("year").asInt();
            int currentYear = LocalDate.now().getYear();

            return Math.max(0, currentYear - manufactureYear);
        } catch (Exception e) {
            // Default to a medium age if JSON is malformed
            return 5;
        }
    }

    /**
     * Applies your specific score logic:
     * 0-3 years: 1.0x (Score 1)
     * 4-7 years: 1.2x (Score 2)
     * 8-12 years: 1.4x (Score 3)
     * >12 years: 1.6x (Score 4)
     */
    private double getAgeMultiplier(int age) {
        if (age <= 3) return 1.0;
        if (age <= 7) return 1.2;
        if (age <= 12) return 1.4;
        return 1.6;
    }
    // Inside RatingEngineServiceImpl.java

    @Override
    public Double calculateEndorsementDelta(Policy policy, Double rawCost) {
        // Handle the BigDecimal to Double conversion for the math
        String detailsJson = policy.getQuote().getInsuredObject().getDetailsJSON();
        int age = calculateVehicleAge(detailsJson);
        double multiplier = getAgeMultiplier(age);
        return rawCost * multiplier;
    }
}