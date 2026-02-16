package com.insuretrack.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private Long userID;
    private String name;
    private String role;
    private String message; // e.g., "Login Successful"
}