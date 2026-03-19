package com.insuretrack.service;

import com.insuretrack.dto.*;
import java.util.List;

public interface CustomerService {
    CustomerResponseDTO createCustomerProfile(CustomerRequestDTO dto);
    CustomerResponseDTO getCustomerProfile(Long id);
    List<CustomerResponseDTO> getAllProfiles();
    void deleteProfile(Long id);
}