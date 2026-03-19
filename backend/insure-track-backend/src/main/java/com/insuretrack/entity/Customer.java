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

    @OneToOne
    @JoinColumn(name = "user_id") // Matches the column with data in your SQL
    private User user;
    private String name;
    private LocalDate dob;
    private String contactInfo;
    @Enumerated(EnumType.STRING)
    private CustomerSegment segment;
    private String email;
    private Long phone;
    @Enumerated(EnumType.STRING)
    private CommonStatus status;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Beneficiary> beneficiaries;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InsuredObject> insuredObjects;

}