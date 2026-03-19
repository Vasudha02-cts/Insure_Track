package com.insuretrack.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
public class PolicyPeriod {
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
}