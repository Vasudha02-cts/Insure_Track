package com.insuretrack.repository;

import com.insuretrack.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser_UserIDAndStatus(Long userID, String status); // Filter unread [cite: 266, 269]
}