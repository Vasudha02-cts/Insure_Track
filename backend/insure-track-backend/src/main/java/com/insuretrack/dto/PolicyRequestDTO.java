package com.insuretrack.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Request to bind/issue a Policy from an APPROVED Quote.
 */
@Data
public class PolicyRequestDTO {
    private Long quoteID;         // The approved Quote for Akhila
    private String policyNumber;  // Can be auto-generated in Service if null
    private LocalDate effectiveDate;
    // premium and expiryDate removed because they come from the Quote!
}