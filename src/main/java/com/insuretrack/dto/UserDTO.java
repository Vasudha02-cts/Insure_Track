package com.insuretrack.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userID;
    private String name;
    private String role; // Customer/Agent/Underwriter/Adjuster/Analyst/Admin
    private String email;
    private String phone;

}