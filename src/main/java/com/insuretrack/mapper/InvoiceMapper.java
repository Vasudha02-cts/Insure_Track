package com.insuretrack.mapper;

import com.insuretrack.dto.InvoiceRequestDTO;
import com.insuretrack.dto.InvoiceResponseDTO;
import com.insuretrack.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice toEntity(InvoiceRequestDTO dto);

    @Mapping(source = "invoiceID", target = "invoiceID")
    InvoiceResponseDTO toResponse(Invoice entity);
}