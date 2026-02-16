package com.insuretrack.service;

import com.insuretrack.dto.*;
import com.insuretrack.entity.User;
import java.util.List;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);

    // FIX: Change 'Customer' to 'CustomerResponseDTO' to match your implementation
    CustomerResponseDTO registerCustomer(CustomerRequestDTO customerRequestDTO);

    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();
}