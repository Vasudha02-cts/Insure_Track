package com.insuretrack.billing.dto;

import com.insuretrack.common.enums.PaymentMethod;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequestDTO {
    private Double amount;
    private PaymentMethod method;
}
