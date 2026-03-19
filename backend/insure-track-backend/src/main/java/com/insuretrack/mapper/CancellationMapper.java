package com.insuretrack.mapper;

import com.insuretrack.dto.CancellationRequestDTO;
import com.insuretrack.dto.CancellationResponseDTO;
import com.insuretrack.entity.Cancellation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CancellationMapper {
    @Mapping(target = "policyID", source = "policy.policyID")
    CancellationResponseDTO toResponse(Cancellation entity);

    Cancellation toEntity(CancellationRequestDTO dto);
}