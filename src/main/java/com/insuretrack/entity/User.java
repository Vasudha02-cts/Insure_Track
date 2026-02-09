package com.insuretrack.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.insuretrack.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "User")
@ToString(exclude = "auditLogs")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String name;
    private String email;
    private String phone;
    private String password; 

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<AuditLog> auditLogs;
}