package com.insuretrack.repository;

import com.insuretrack.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Finds notifications for a specific user, sorted by newest first
    List<Notification> findByUserIDOrderByCreatedDateDesc(Long userID);

    // Finds unread notifications
    List<Notification> findByUserIDAndStatus(Long userID, String status);
}