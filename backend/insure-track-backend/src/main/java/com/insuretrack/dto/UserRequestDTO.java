package com.insuretrack.dto;

import com.insuretrack.entity.enums.UserRole;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
}