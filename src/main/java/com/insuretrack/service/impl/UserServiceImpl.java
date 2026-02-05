package com.insuretrack.service.impl;

import com.insuretrack.entity.User;
import com.insuretrack.entity.AuditLog;
import com.insuretrack.repository.UserRepository;
import com.insuretrack.repository.AuditLogRepository;
import com.insuretrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public User registerUser(User user) {
        // 1. Save the User [cite: 38]
        User savedUser = userRepository.save(user);

        // 2. Create Audit Log Entry [cite: 39]
        AuditLog log = new AuditLog();
        log.setUser(savedUser); // Maps to UserID [cite: 51]
        log.setAction("REGISTER"); // [cite: 53]
        log.setResource("USER_MODULE"); // [cite: 54]
        log.setTimestamp(LocalDateTime.now()); // [cite: 55]
        log.setMetadata("New user registered with role: " + savedUser.getRole()); // [cite: 57]

        auditLogRepository.save(log);

        return savedUser;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}