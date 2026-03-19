package com.insuretrack.claims;

import com.insuretrack.claims.dto.*;
import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.repository.ClaimRepository;
import com.insuretrack.claims.service.ClaimServiceImpl;
import com.insuretrack.common.enums.ClaimStatus;
import com.insuretrack.customer.entity.Customer;
import com.insuretrack.notification.service.NotificationService;
import com.insuretrack.policy.entity.Policy;
import com.insuretrack.policy.repository.PolicyRepository;
import com.insuretrack.quote.entity.Quote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClaimServiceImplTest {

    @Mock private ClaimRepository claimRepository;
    @Mock private PolicyRepository policyRepository;
    @Mock private NotificationService notificationService;

    @InjectMocks private ClaimServiceImpl claimService;

    @Test
    void createClaim_ShouldSucceed_WhenPolicyExists() {
        // 1. Arrange
        ClaimRequestDTO dto = new ClaimRequestDTO();
        dto.setPolicyId(1L);
        dto.setClaimType("AUTO");

        // Build the object chain to prevent NPE
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomerId(500L); // The ID the notification service needs

        Quote mockQuote = new Quote();
        mockQuote.setCustomer(mockCustomer);

        Policy policy = new Policy();
        policy.setPolicyId(1L);
        policy.setPolicyNumber("POL-101");
        policy.setQuote(mockQuote); // Link the quote to the policy

        // Tell Mockito to return this fully-built policy
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        // 2. Act
        ClaimResponseDTO response = claimService.createClaim(dto);

        // 3. Assert
        assertNotNull(response);
        assertEquals("OPEN", response.getStatus());

        // Verify that the notification was sent to the correct customer ID
        verify(notificationService).createNotification(
                eq(500L),
                contains("POL-101"),
                any()
        );
        verify(claimRepository, times(1)).save(any(Claim.class));
    }

    @Test
    void approveClaim_ShouldThrowException_WhenStatusIsNotInvestigating() {
        // Arrange: Claim is currently OPEN (not Investigating)
        Claim claim = Claim.builder().claimId(1L).status(ClaimStatus.OPEN).build();
        when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            claimService.approveClaim(1L);
        });

        assertEquals("Invalid status transition", exception.getMessage());
    }
}