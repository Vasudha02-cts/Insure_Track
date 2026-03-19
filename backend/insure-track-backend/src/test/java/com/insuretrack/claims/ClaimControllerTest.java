package com.insuretrack.claims;

import com.insuretrack.claims.controller.ClaimController;
import com.insuretrack.claims.dto.ClaimResponseDTO;
import com.insuretrack.claims.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClaimControllerTest {

    private ClaimController claimController;

    @Mock private ClaimService claimService;
    @Mock private ReserveService reserveService;
    @Mock private SettlementService settlementService;
    @Mock private EvidenceService evidenceService;
    @Mock private AssignmentService assignmentService;

    @BeforeEach
    void setUp() {
        // Manually creating the controller with mocked services
        claimController = new ClaimController(
                claimService, reserveService, settlementService, evidenceService, assignmentService
        );
    }

    @Test
    void getClaim_ShouldReturnData_WhenIdIsValid() {
        // Arrange
        ClaimResponseDTO expected = ClaimResponseDTO.builder().claimId(1L).status("OPEN").build();
        when(claimService.getClaim(1L)).thenReturn(expected);

        // Act
        ClaimResponseDTO result = claimController.get(1L);

        // Assert
        assertEquals(1L, result.getClaimId());
        assertEquals("OPEN", result.getStatus());
    }
}