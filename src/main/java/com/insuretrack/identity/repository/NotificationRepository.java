package com.insuretrack.identity.repository;

import com.insuretrack.identity.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    public List<Notification> findByUser_UserID(Long userID);
}
