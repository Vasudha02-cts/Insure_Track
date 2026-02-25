package com.insuretrack.mapper;

import com.insuretrack.dto.RefundRequestDTO;
import com.insuretrack.dto.RefundResponseDTO;
import com.insuretrack.entity.Refund;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RefundMapper {
    @Mapping(target = "payment.paymentID", source = "paymentId")
    Refund toEntity(RefundRequestDTO dto);

    @Mapping(source = "payment.paymentID", target = "paymentId")
    RefundResponseDTO toResponse(Refund entity);
}