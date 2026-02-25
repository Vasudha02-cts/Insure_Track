package com.insuretrack.mapper;

import com.insuretrack.dto.EndorsementRequestDTO;
import com.insuretrack.dto.EndorsementResponseDTO;
import com.insuretrack.entity.Endorsement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EndorsementMapper {

    // Map DTO to Entity (Status and logic handled in Service)
    @Mapping(target = "endorsementID", ignore = true)
    @Mapping(target = "status", ignore = true)
    Endorsement toEntity(EndorsementRequestDTO dto);

    // Map Entity to Response DTO
    // Note: If change_type and status are Enums in Entity and Strings in DTO,
    // MapStruct handles .name() automatically.
    EndorsementResponseDTO toResponse(Endorsement entity);
}