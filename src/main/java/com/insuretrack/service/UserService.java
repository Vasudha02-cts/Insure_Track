package com.insuretrack.service;

import com.insuretrack.dto.*;
import com.insuretrack.entity.User;
import java.util.List;

public interface UserService {
    // For Staff (Underwriters, Agents, Admin)
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);

    // For Akhila (Creates User record AND Customer Profile record)
    CustomerResponseDTO registerCustomer(CustomerRequestDTO customerRequestDTO);

    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();
}