package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductAvailabilityRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductAvailabilityResponseDTO;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-availability")
@RequiredArgsConstructor
public class ProductAvailabilityController {

    private final ProductAvailabilityService service;

    @PostMapping
    public ResponseEntity<ProductAvailabilityResponseDTO> createOrUpdate(
            @RequestBody ProductAvailabilityRequestDTO dto) {
        return ResponseEntity.ok(service.createOrUpdate(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductAvailabilityResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductAvailabilityResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
