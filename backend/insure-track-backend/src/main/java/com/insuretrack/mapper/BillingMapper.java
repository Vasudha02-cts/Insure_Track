package com.insuretrack.mapper;

import com.insuretrack.dto.InvoiceResponseDTO;
import com.insuretrack.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillingMapper {
    @Mapping(target = "policyNumber", source = "policy.policyNumber")
    InvoiceResponseDTO toInvoiceResponse(Invoice entity);
}