package com.insuretrack.claims;

import com.insuretrack.claims.entity.Settlement;
import com.insuretrack.claims.repository.SettlementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SettlementRepositoryTest {

    @Mock
    private SettlementRepository settlementRepository;

    @Test
    void findByClaimClaimId_Success() {
        // Arrange
        Settlement mockSettlement = Settlement.builder()
                .settlementId(1L)
                .settlementAmount(1500.0)
                .status("COMPLETED")
                .build();

        when(settlementRepository.findByClaimClaimId(1L)).thenReturn(Optional.of(mockSettlement));

        // Act
        Optional<Settlement> result = settlementRepository.findByClaimClaimId(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1500.0, result.get().getSettlementAmount());
    }
}