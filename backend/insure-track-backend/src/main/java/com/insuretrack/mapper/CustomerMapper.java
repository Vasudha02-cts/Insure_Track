package com.insuretrack.mapper;

import com.insuretrack.dto.CustomerRequestDTO;
import com.insuretrack.dto.CustomerResponseDTO;
import com.insuretrack.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BeneficiaryMapper.class, InsuredObjectMapper.class})
public interface CustomerMapper {

    @Mapping(target = "customerID", ignore = true)
    @Mapping(target = "user", ignore = true) // Linked manually in Service via UserID
    @Mapping(target = "beneficiaries", source = "beneficiaries")
    @Mapping(target = "insuredObjects", source = "insuredObjects")
    Customer toEntity(CustomerRequestDTO dto);

    @Mapping(target = "userID", source = "user.userID")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    CustomerResponseDTO toResponse(Customer customer);
}