package com.insuretrack.mapper;

import com.insuretrack.dto.*;
import com.insuretrack.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    // Existing mappings
    @Mapping(target = "policy.policyID", source = "policyID")
    @Mapping(target = "incidentDate", source = "incidentDate", dateFormat = "yyyy-MM-dd")
    Claim toEntity(ClaimRequestDTO dto);

    ClaimResponseDTO toResponse(Claim claim);

    // NEW MAPPINGS
    @Mapping(target = "claimID", source = "claim.claimID")
    ClaimAssignmentResponseDTO toAssignmentResponse(ClaimAssignment assignment);

    @Mapping(target = "claimID", source = "claim.claimID")
    SettlementResponseDTO toSettlementResponse(Settlement settlement);

    @Mapping(target = "claimID", source = "claim.claimID")
    ReserveResponseDTO toReserveResponse(Reserve reserve);

    @Mapping(target = "claimID", source = "claim.claimID")
    EvidenceResponseDTO toEvidenceResponse(Evidence evidence);

    Evidence toEvidenceEntity(EvidenceRequestDTO dto);

    @Mapping(target = "claimDetails", source = "claim")
    @Mapping(target = "assignments", source = "assignments")
    @Mapping(target = "reserves", source = "reserves")
    @Mapping(target = "evidence", source = "evidence")
    @Mapping(target = "settlement", source = "settlement")
    ClaimSummaryDTO toSummaryDto(Claim claim,
                                 List<ClaimAssignment> assignments,
                                 List<Reserve> reserves,
                                 List<Evidence> evidence,
                                 Settlement settlement);
}
