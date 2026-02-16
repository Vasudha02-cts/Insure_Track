package com.insuretrack.entity;

import com.insuretrack.entity.enums.CommonStatus;
import com.insuretrack.entity.enums.CustomerSegment;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerID;

    private Long userID; // Simple Long to link to User Table
    private String name;
    private LocalDate dob;
    private String contactInfo; // Simple String instead of Embeddable

    @Enumerated(EnumType.STRING)
    private CustomerSegment segment;

    @Enumerated(EnumType.STRING)
    private CommonStatus status;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Beneficiary> beneficiaries;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InsuredObject> insuredObjects;
}