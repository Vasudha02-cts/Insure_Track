package com.insuretrack.user.dto;

import com.insuretrack.common.enums.UserRole;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
}
