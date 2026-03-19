package com.insuretrack.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long userID;
    private String name;
    private String email;
    private String phone;
    private String role;
}
