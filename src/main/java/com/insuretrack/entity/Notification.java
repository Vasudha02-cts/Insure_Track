package com.insuretrack.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;
    private String message;
    private String category;
    private String status;
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
}
