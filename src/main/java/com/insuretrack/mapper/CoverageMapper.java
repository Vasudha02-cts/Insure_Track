package com.insuretrack.mapper;

import com.insuretrack.dto.CoverageRequestDTO;
import com.insuretrack.dto.CoverageResponseDTO;
import com.insuretrack.entity.Coverage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CoverageMapper {

    @Mapping(target = "productID", source = "product.productID")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "limit", source = "limitAmount")
    CoverageResponseDTO toDto(Coverage coverage);

    @Mapping(target = "coverageID", ignore = true)
    @Mapping(target = "product", ignore = true) // Set manually in Service via ID
    @Mapping(target = "limitAmount", source = "limit")
    Coverage toEntity(CoverageRequestDTO requestDto);

}