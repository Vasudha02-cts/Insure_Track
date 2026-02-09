package com.insuretrack.entity;

import com.insuretrack.entity.embeddable.ContactDetails;
import com.insuretrack.entity.enums.CustomerSegment;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "Customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerID;

    @OneToOne
    @JoinColumn(name = "UserID")
    private User user;

    private String name;
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "Segment")
    private CustomerSegment segment;
    private String status;

    @Embedded
    private ContactDetails contactDetails;
}