package com.insuretrack.mapper;

import com.insuretrack.dto.BeneficiaryRequestDTO;
import com.insuretrack.dto.BeneficiaryResponseDTO;
import com.insuretrack.entity.Beneficiary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeneficiaryMapper {
    Beneficiary toEntity(BeneficiaryRequestDTO dto);
    BeneficiaryResponseDTO toResponse(Beneficiary entity);
}