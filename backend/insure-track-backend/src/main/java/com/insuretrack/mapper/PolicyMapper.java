package com.insuretrack.mapper;

import com.insuretrack.dto.PolicyRequestDTO;
import com.insuretrack.dto.PolicyResponseDTO;
import com.insuretrack.entity.Policy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PolicyMapper {
    @Mapping(target = "quoteID", source = "quote.quoteID")
    @Mapping(target = "customerID", source = "customer.customerID")
    @Mapping(target = "totalPremium", source = "premium") // Source is entity, target is DTO
    PolicyResponseDTO toResponse(Policy policy);

    @Mapping(target = "quote", ignore = true)
    @Mapping(target = "customer", ignore = true)
    Policy toEntity(PolicyRequestDTO dto);
}
