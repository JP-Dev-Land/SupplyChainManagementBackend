package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.ai.ProductAiImageRequestDto;
import com.jpdevland.supply_chain_mgmt.backend.dto.ai.ProductAiImageResponseDto;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductAiImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product-ai-images")
@RequiredArgsConstructor
public class ProductAiImageController {

    private final ProductAiImageService service;

    @PostMapping
    public ResponseEntity<ProductAiImageResponseDto> create(@RequestBody ProductAiImageRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductAiImageResponseDto>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getByProductId(productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
