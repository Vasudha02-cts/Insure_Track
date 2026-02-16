package com.insuretrack.mapper;

import com.insuretrack.dto.*;
import com.insuretrack.entity.AuditLog;
import com.insuretrack.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // Existing mappings for registration
    User toEntity(UserRequestDTO dto);
    UserResponseDTO toResponse(User user);
    User toEntityFromCustomerRequest(CustomerRequestDTO dto);

    // NEW: Mapping for Login Response
    LoginResponseDTO toLoginResponse(User user);

    // NEW: Mapping for Audit Logs
    @Mapping(target = "userID", source = "user.userID")
    AuditLogResponseDTO toAuditResponse(AuditLog log);
}