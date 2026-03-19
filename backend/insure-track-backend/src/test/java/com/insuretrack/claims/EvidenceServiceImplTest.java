package com.insuretrack.claims;

import com.insuretrack.claims.dto.EvidenceRequestDTO;
import com.insuretrack.claims.dto.EvidenceResponseDTO;
import com.insuretrack.claims.entity.Claim;
import com.insuretrack.claims.entity.Evidence;
import com.insuretrack.claims.repository.ClaimRepository;
import com.insuretrack.claims.repository.EvidenceRepository;
import com.insuretrack.claims.service.EvidenceServiceImpl;
import com.insuretrack.common.enums.ClaimStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvidenceServiceImplTest {

    @Mock private EvidenceRepository evidenceRepository;
    @Mock private ClaimRepository claimRepository;
    @Mock private MultipartFile mockFile;

    @InjectMocks private EvidenceServiceImpl evidenceService;

    @Test
    void uploadEvidence_ThrowsException_WhenClaimIsClosed() {
        // Arrange
        Long claimId = 1L;
        Claim closedClaim = Claim.builder().claimId(claimId).status(ClaimStatus.CLOSED).build();

        when(claimRepository.findById(claimId)).thenReturn(Optional.of(closedClaim));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> evidenceService.uploadEvidence(claimId, new EvidenceRequestDTO(), mockFile));

        assertEquals("Cannot upload evidence to closed claim", ex.getMessage());
    }

    @Test
    void uploadEvidence_ThrowsException_WhenFileNotFound() {
        // Arrange
        Long claimId = 1L;
        when(claimRepository.findById(claimId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> evidenceService.uploadEvidence(claimId, new EvidenceRequestDTO(), mockFile));
    }
}