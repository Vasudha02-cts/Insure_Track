package com.insuretrack.mapper;

import com.insuretrack.dto.InsuredObjectRequestDTO;
import com.insuretrack.dto.InsuredObjectResponseDTO;
import com.insuretrack.entity.InsuredObject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InsuredObjectMapper {
    InsuredObject toEntity(InsuredObjectRequestDTO dto);
    InsuredObjectResponseDTO toResponse(InsuredObject entity);
}
