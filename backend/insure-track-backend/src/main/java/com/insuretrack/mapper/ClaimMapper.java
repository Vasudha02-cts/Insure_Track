package com.insuretrack.mapper;

import com.insuretrack.dto.*;
import com.insuretrack.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClaimMapper {
    @Mapping(target = "policyID", source = "policy.policyID")
    ClaimResponseDTO toResponse(Claim entity);

    @Mapping(target = "claimID", source = "claim.claimID")
    @Mapping(target = "setDate", source = "setDate")
    ReserveResponseDTO toReserveResponse(Reserve entity);

    // Added for Settlement mapping
    @Mapping(target = "claimID", source = "claim.claimID")
    SettlementResponseDTO toSettlementResponse(Settlement entity);
}