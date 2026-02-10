package com.insuretrack.identity.service;

import com.insuretrack.identity.entity.Notification;
import com.insuretrack.identity.entity.User;
import com.insuretrack.identity.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    public void createNotification(User user,String message,String category){
        Notification n=new Notification();
        n.setUser(user);
        n.setMessage(message);
        n.setCategory(category);
        n.setStatus("UNREAD");
        n.setCreatedDate(LocalDateTime.now());
        notificationRepository.save(n);
    }
    public List<Notification> getUserNotifications(Long userID){
        return notificationRepository.findByUser_UserID(userID);
    }
}
