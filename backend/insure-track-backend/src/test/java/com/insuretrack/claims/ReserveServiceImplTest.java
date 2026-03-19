package com.insuretrack.claims;

import com.insuretrack.claims.dto.ReserveRequestDTO;
import com.insuretrack.claims.dto.ReserveResponseDTO;
import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.entity.Reserve;
import com.insuretrack.claims.repository.ClaimRepository;
import com.insuretrack.claims.repository.ReserveRepository;
import com.insuretrack.claims.service.ReserveServiceImpl;
import com.insuretrack.common.enums.ClaimStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveServiceImplTest {

    @Mock private ReserveRepository reserveRepository;
    @Mock private ClaimRepository claimRepository;

    @InjectMocks private ReserveServiceImpl reserveService;

    @Test
    void createReserve_Success_WhenClaimInReview() {
        // Arrange
        Long claimId = 1L;
        ReserveRequestDTO dto = new ReserveRequestDTO();
        dto.setAmount(3000.0);

        Claim claim = Claim.builder()
                .claimId(claimId)
                .status(ClaimStatus.INVESTIGATING) // Required status
                .build();

        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
        // Mock save to return the object passed to it
        when(reserveRepository.save(any(Reserve.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ReserveResponseDTO response = reserveService.createReserve(claimId, dto);

        // Assert
        assertNotNull(response);
        assertEquals(3000.0, response.getAmount());
        assertEquals("OPEN", response.getStatus());
        verify(reserveRepository, times(1)).save(any(Reserve.class));
    }

    @Test
    void createReserve_ThrowsException_WhenClaimIsStillOpen() {
        // Arrange: Claim is OPEN, but needs to be INVESTIGATING
        Long claimId = 1L;
        Claim claim = Claim.builder().claimId(claimId).status(ClaimStatus.OPEN).build();
        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reserveService.createReserve(claimId, new ReserveRequestDTO()));

        assertEquals("Reserve allowed only in UNDER_REVIEW stage", ex.getMessage());
    }
}