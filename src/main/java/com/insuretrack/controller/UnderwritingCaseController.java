package com.insuretrack.controller;

import com.insuretrack.entity.UnderwritingCase;
import com.insuretrack.service.UnderwritingCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/underwriting")
public class UnderwritingCaseController {

    @Autowired private UnderwritingCaseService service;

    @PostMapping
    public UnderwritingCase submitCase(@RequestBody UnderwritingCase uwCase) {
        return service.createCase(uwCase);
    }

    @PutMapping("/{id}")
    public UnderwritingCase finalizeDecision(@PathVariable Long id, @RequestBody UnderwritingCase update) {
        return service.updateDecision(id, update);
    }

    @GetMapping("/status/{status}")
    public List<UnderwritingCase> getByStatus(@PathVariable UnderwritingCase.DecisionStatus status) {
        return service.getCasesByDecision(status);
    }
}