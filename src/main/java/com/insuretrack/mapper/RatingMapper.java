package com.insuretrack.mapper;

import com.insuretrack.dto.RatingRuleRequestDTO;
import com.insuretrack.dto.RatingRuleResponseDTO;
import com.insuretrack.entity.RatingRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    @Mapping(target = "productID", source = "product.productID")
    RatingRuleResponseDTO toResponse(RatingRule entity);

    @Mapping(target = "product", ignore = true)
    RatingRule toEntity(RatingRuleRequestDTO dto);
}