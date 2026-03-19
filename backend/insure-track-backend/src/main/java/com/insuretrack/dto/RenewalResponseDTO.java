package com.insuretrack.dto;

import com.insuretrack.entity.enums.RenewalStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Standard API response for Renewal. */
public class RenewalResponseDTO {
    public Long renewalID;
    public Long policyID;
    public BigDecimal proposedPremium;
    public String proposedCoveragesJSON;
    public LocalDate offerDate;
    public RenewalStatus status;
}