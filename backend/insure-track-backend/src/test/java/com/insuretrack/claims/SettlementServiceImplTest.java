package com.insuretrack.claims;

import com.insuretrack.claims.dto.SettlementRequestDTO;
import com.insuretrack.claims.dto.SettlementResponseDTO;
import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.entity.Settlement;
import com.insuretrack.claims.repository.ClaimRepository;
import com.insuretrack.claims.repository.SettlementRepository;
import com.insuretrack.claims.service.SettlementServiceImpl;
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
class SettlementServiceImplTest {

    @Mock private SettlementRepository settlementRepository;
    @Mock private ClaimRepository claimRepository;

    @InjectMocks private SettlementServiceImpl settlementService;

    @Test
    void createSettlement_Success_WhenClaimIsSettled() {
        // Arrange
        Long claimId = 1L;
        SettlementRequestDTO dto = new SettlementRequestDTO();
        dto.setSettlementAmount(5000.0);

        Claim claim = Claim.builder()
                .claimId(claimId)
                .status(ClaimStatus.SETTLED) // Correct status for settlement
                .build();

        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
        when(settlementRepository.save(any(Settlement.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        SettlementResponseDTO response = settlementService.createSettlement(claimId, dto);

        // Assert
        assertNotNull(response);
        assertEquals(5000.0, response.getSettlementAmount());
        verify(settlementRepository).save(any(Settlement.class));
    }

    @Test
    void createSettlement_ThrowsException_WhenClaimNotApproved() {
        // Arrange: Claim is still INVESTIGATING
        Long claimId = 1L;
        Claim claim = Claim.builder().claimId(claimId).status(ClaimStatus.INVESTIGATING).build();
        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> settlementService.createSettlement(claimId, new SettlementRequestDTO()));

        assertEquals("Settlement allowed only after APPROVAL", ex.getMessage());
    }
}