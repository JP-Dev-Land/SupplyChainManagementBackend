package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.ProductRecommendationDto;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductRecommendationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor // Injects ProductRecommendationService
public class ProductRecommendationController {

    private final ProductRecommendationService recommendationService;

    // GET /api/v1/recommendations
    @GetMapping
    public ResponseEntity<Page<ProductRecommendationDto>> getAllRecommendations(
            @PageableDefault(size = 20, sort = "recommendedAt") Pageable pageable) {
        Page<ProductRecommendationDto> recommendations = recommendationService.getAllRecommendations(pageable);
        return ResponseEntity.ok(recommendations);
    }

    // GET /api/v1/recommendations/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductRecommendationDto> getRecommendationById(@PathVariable Long id) {
        ProductRecommendationDto recommendation = recommendationService.getRecommendationById(id);
        return ResponseEntity.ok(recommendation);
    }

    // GET /api/v1/recommendations/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ProductRecommendationDto>> getRecommendationsByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "score", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        Page<ProductRecommendationDto> recommendations = recommendationService.getRecommendationsByUserId(userId, pageable);
        return ResponseEntity.ok(recommendations);
    }

    // GET /api/v1/recommendations/product/{productId}
    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<ProductRecommendationDto>> getRecommendationsByProduct(
            @PathVariable Long productId,
            @PageableDefault(size = 10, sort = "recommendedAt") Pageable pageable) {
        Page<ProductRecommendationDto> recommendations = recommendationService.getRecommendationsByProductId(productId, pageable);
        return ResponseEntity.ok(recommendations);
    }

    // POST /api/v1/recommendations
    @PostMapping
    public ResponseEntity<ProductRecommendationDto> createRecommendation(
            @Valid @RequestBody ProductRecommendationDto recommendationDto) {
        ProductRecommendationDto createdRecommendation = recommendationService.createRecommendation(recommendationDto);
        // Optional: Create Location header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdRecommendation.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdRecommendation);
    }

    // PUT /api/v1/recommendations/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProductRecommendationDto> updateRecommendation(
            @PathVariable Long id,
            @Valid @RequestBody ProductRecommendationDto recommendationDto) {
        ProductRecommendationDto updatedRecommendation = recommendationService.updateRecommendation(id, recommendationDto);
        return ResponseEntity.ok(updatedRecommendation);
    }

    // DELETE /api/v1/recommendations/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendationById(@PathVariable Long id) {
        recommendationService.deleteRecommendation(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE /api/v1/recommendations/user/{userId}/product/{productId}/algo/{algorithm}
    @DeleteMapping("/user/{userId}/product/{productId}/algo/{algorithm}")
    public ResponseEntity<Void> deleteRecommendationByDetails(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @PathVariable String algorithm) {
        recommendationService.deleteRecommendation(userId, productId, algorithm);
        return ResponseEntity.noContent().build();
    }
}
