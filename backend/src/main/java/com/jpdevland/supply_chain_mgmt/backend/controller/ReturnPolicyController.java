package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.returnpolicy.ReturnPolicyCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.returnpolicy.ReturnPolicyDTO;
import com.jpdevland.supply_chain_mgmt.backend.service.ReturnPolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/return-policies")
@RequiredArgsConstructor
public class ReturnPolicyController {

    private final ReturnPolicyService returnPolicyService;

    @PostMapping
    public ResponseEntity<ReturnPolicyDTO> createReturnPolicy(@Valid @RequestBody ReturnPolicyCreateRequest request) {
        ReturnPolicyDTO createdPolicy = returnPolicyService.createReturnPolicy(request);
        return new ResponseEntity<>(createdPolicy, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReturnPolicyDTO>> getReturnPoliciesByProductId(@PathVariable Long productId) {
        List<ReturnPolicyDTO> policies = returnPolicyService.getAllReturnPoliciesByProductId(productId);
        return ResponseEntity.ok(policies);
    }

    @DeleteMapping("/{policyId}")
    public ResponseEntity<Void> deleteReturnPolicy(@PathVariable Long policyId) {
        returnPolicyService.deleteReturnPolicy(policyId);
        return ResponseEntity.noContent().build();
    }
}