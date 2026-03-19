package com.insuretrack.dto;

import jakarta.validation.constraints.NotNull;

/** Accept/Decline a renewal offer. */
public class RenewalRequestDTO {
    @NotNull public Long renewalID;
    /** true = Accept, false = Decline */
    @NotNull public Boolean accept;
}