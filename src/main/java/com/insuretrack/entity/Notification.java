package com.insuretrack.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Notification")
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;

    private Long userID; // Links to UserID
    private String message;
    private String category; // Renewal/Billing/Claim/Underwriting
    private String status;   // Unread/Read
    private LocalDateTime createdDate;
}