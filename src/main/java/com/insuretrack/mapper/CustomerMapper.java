package com.insuretrack.mapper;

import com.insuretrack.dto.CustomerRequestDTO;
import com.insuretrack.dto.CustomerResponseDTO;
import com.insuretrack.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BeneficiaryMapper.class, InsuredObjectMapper.class})
public interface CustomerMapper {

    // Map Request DTO -> Entity
    @Mapping(target = "beneficiaries", source = "beneficiaries")
    @Mapping(target = "insuredObjects", source = "insuredObjects")
    Customer toEntity(CustomerRequestDTO dto);

    // Map Entity -> Response DTO
    @Mapping(target = "email", ignore = true) // Handled in Service
    @Mapping(target = "phone", ignore = true) // Handled in Service
    CustomerResponseDTO toResponse(Customer customer);
}