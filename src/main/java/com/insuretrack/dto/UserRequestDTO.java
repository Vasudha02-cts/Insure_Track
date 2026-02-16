package com.insuretrack.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String password; // Only in Request!
    private String role; // Customer/Agent/Underwriter, etc. [cite: 44, 275]
}