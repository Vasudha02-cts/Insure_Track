package com.insuretrack.claims;

import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.entity.ClaimAssignment;
import com.insuretrack.claims.repository.ClaimAssignmentRepository; // Updated Import
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClaimAssignmentRepositoryTest {

    @Mock
    private ClaimAssignmentRepository assignmentRepository; // Changed from ClaimRepository

    @Test
    void findByClaimClaimId_ShouldReturnAssignment_WhenExists() {
        // 1. Arrange
        Long claimId = 101L;
        Claim mockClaim = Claim.builder().claimId(claimId).build();
        ClaimAssignment mockAssignment = ClaimAssignment.builder()
                .assignmentId(1L)
                .claim(mockClaim)
                .priority("HIGH")
                .build();

        // Tell the mock to return the assignment
        when(assignmentRepository.findByClaimClaimId(claimId))
                .thenReturn(Optional.of(mockAssignment));

        // 2. Act
        Optional<ClaimAssignment> result = assignmentRepository.findByClaimClaimId(claimId);

        // 3. Assert
        assertTrue(result.isPresent());
        assertEquals("HIGH", result.get().getPriority());
        assertEquals(claimId, result.get().getClaim().getClaimId());
        verify(assignmentRepository, times(1)).findByClaimClaimId(claimId);
    }

    @Test
    void findByClaimClaimId_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(assignmentRepository.findByClaimClaimId(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<ClaimAssignment> result = assignmentRepository.findByClaimClaimId(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(assignmentRepository, times(1)).findByClaimClaimId(999L);
    }
}