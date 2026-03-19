package com.insuretrack.mapper;

import com.insuretrack.dto.UnderwritingResponseDTO;
import com.insuretrack.entity.UnderwritingCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UnderwritingMapper {

    @Mapping(target = "decision", source = "decision") // MapStruct handles String to Enum
    @Mapping(target = "riskAssessmentNotes", source = "riskAssessment")
    @Mapping(target = "quoteID", source = "quote.quoteID")
    UnderwritingResponseDTO toResponse(UnderwritingCase entity);
}