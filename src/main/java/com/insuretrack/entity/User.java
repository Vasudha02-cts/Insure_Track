package com.insuretrack.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.TypeBinderType;

import java.util.List;


@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    private String name;
    private String role;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AuditLog> auditLogs;
}
