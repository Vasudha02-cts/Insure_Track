package com.insuretrack.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerID;
    private Long userID;
    private String name;
    private LocalDate dob;
    private String contactInfo;
    private String segment;
    private String status;
}
