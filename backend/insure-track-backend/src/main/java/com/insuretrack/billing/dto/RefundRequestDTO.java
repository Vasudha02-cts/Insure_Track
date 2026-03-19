package com.insuretrack.billing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundRequestDTO {
    private Double amount;
    private String reason;
}
