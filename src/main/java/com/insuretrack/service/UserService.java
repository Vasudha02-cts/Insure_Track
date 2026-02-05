package com.insuretrack.service;

import com.insuretrack.entity.User;
import java.util.List;

public interface UserService {
    User registerUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
}