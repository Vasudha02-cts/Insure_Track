package com.insuretrack.mapper;

import com.insuretrack.dto.QuoteRequestDTO;
import com.insuretrack.dto.QuoteResponseDTO;
import com.insuretrack.entity.Quote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "insuredObject", ignore = true)
    @Mapping(target = "quoteID", ignore = true)
    Quote toEntity(QuoteRequestDTO dto);

    @Mapping(target = "customerID", source = "customer.customerID")
    @Mapping(target = "insuredObjectID", source = "insuredObject.objectID")
    @Mapping(target = "calculatedPremium", source = "premium")
    QuoteResponseDTO toResponse(Quote entity);
}