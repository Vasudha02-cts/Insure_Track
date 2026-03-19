package com.insuretrack.billing.dto;

import com.insuretrack.common.enums.PaymentMethod;
import com.insuretrack.common.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@Builder
public class PaymentResponseDTO {
    private Long paymentId;
    private Long invoiceId;
    private Double amount;
    private LocalDate paidDate;
    private PaymentMethod method;
    private PaymentStatus status;
}
