package com.insuretrack.mapper;

import com.insuretrack.dto.InsuredObjectRequestDTO;
import com.insuretrack.dto.InsuredObjectResponseDTO;
import com.insuretrack.entity.InsuredObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InsuredObjectMapper {

    // DTO -> Entity
    @Mapping(target = "objectID", ignore = true)
    @Mapping(target = "customer", ignore = true)
    InsuredObject toEntity(InsuredObjectRequestDTO dto);

    // Entity -> DTO (This is what your service is looking for)
    @Mapping(source = "objectID", target = "objectID")
    InsuredObjectResponseDTO toResponse(InsuredObject entity);
}