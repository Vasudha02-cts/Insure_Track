package com.insuretrack.dto;

import com.insuretrack.entity.enums.QuoteStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class QuoteResponseDTO {
    private Long quoteID;
    private Long customerID;
    private Long productID;
    private Long insuredObjectID;
    private String coveragesJSON;
    private Double calculatedPremium; // Changed to Double
    private LocalDate expiryDate;
    private QuoteStatus status;
}