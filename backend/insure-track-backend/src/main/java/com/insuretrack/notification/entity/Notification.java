package com.insuretrack.notification.entity;

import com.insuretrack.common.enums.NotificationCategory;
import com.insuretrack.common.enums.NotificationStatus;
import com.insuretrack.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 50) // Explicitly set length
    private NotificationCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)   // Do the same for status
    private NotificationStatus status;

    private LocalDateTime createdDate;
}