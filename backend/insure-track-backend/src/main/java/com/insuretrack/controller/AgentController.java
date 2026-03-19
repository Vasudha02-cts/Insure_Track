package com.insuretrack.controller;

import com.insuretrack.dto.InsuredObjectAgentDTO;
import com.insuretrack.dto.InsuredObjectResponseDTO;
import com.insuretrack.service.InsuredObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {
    private final InsuredObjectService objectService;

    @PostMapping("/objects")
    public ResponseEntity<InsuredObjectResponseDTO> registerAndValuate(@RequestBody InsuredObjectAgentDTO dto) {
        // This service method will set the status to ACTIVE automatically
        return ResponseEntity.ok(objectService.agentCreateObject(dto));
    }
}