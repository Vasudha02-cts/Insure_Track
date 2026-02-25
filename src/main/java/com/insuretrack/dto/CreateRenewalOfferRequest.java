package com.insuretrack.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Request to create a Renewal offer. */
public class CreateRenewalOfferRequest {
    @NotNull public Long policyID;
    @NotNull @PositiveOrZero public BigDecimal proposedPremium;
    public String proposedCoveragesJSON; // optional
    @NotNull public LocalDate offerDate;
}