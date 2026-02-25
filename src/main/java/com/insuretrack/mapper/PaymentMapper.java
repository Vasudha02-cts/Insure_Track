package com.insuretrack.mapper;

import com.insuretrack.dto.PaymentReceiptDTO;
import com.insuretrack.dto.PaymentRequestDTO;
import com.insuretrack.dto.PaymentResponseDTO;
import com.insuretrack.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "invoice.invoiceID", source = "invoiceID")
    @Mapping(target = "paidDate", expression = "java(java.time.LocalDateTime.now())")
    Payment toEntity(PaymentRequestDTO dto);

    @Mapping(source = "invoice.invoiceID", target = "invoiceID")
    PaymentResponseDTO toResponse(Payment entity);

    @Mapping(target = "invoiceID", source = "invoice.invoiceID")
    @Mapping(target = "amountPaid", source = "amount")
    @Mapping(target = "transactionDate", expression = "java(payment.getPaidDate().toLocalDate())") // Convert for Receipt
    @Mapping(target = "policyNumber", expression = "java(\"POL-\" + payment.getInvoice().getPolicy().getPolicyID())")
    @Mapping(target = "receiptNumber", expression = "java(\"REC-\" + payment.getPaymentID())")
    PaymentReceiptDTO toReceiptDTO(Payment payment);
}