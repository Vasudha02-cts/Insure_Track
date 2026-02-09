package com.insuretrack.service;

import com.insuretrack.dto.CustomerDTO;
import com.insuretrack.dto.UserDTO;
import com.insuretrack.entity.Customer;
import com.insuretrack.entity.User;
import java.util.List;
import com.insuretrack.entity.enums.CustomerSegment;

public interface UserService {
    User registerUser(User user);

    Customer registerCustomer(UserDTO userDTO, CustomerDTO customerDTO);
    User getUserById(Long id);
    List<User> getAllUsers();
}