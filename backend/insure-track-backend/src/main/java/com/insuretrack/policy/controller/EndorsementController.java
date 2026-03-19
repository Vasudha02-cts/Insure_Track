package com.insuretrack.policy.controller;

import com.insuretrack.policy.dto.EndorsementRequestDTO;
import com.insuretrack.policy.dto.EndorsementResponseDTO;
import com.insuretrack.policy.service.EndorsementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/endorsements")
@RequiredArgsConstructor
public class EndorsementController {
    private final EndorsementService endorsementService;
    @PostMapping
    public ResponseEntity<EndorsementResponseDTO> create(@RequestBody EndorsementRequestDTO requestDTO){
        return ResponseEntity.ok(endorsementService.create(requestDTO));
    }
}
