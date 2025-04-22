package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductVariantDTO;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products/{productId}/variants")
@RequiredArgsConstructor
@Slf4j
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @PostMapping
    public ResponseEntity<ProductVariantDTO> createProductVariant(
            @PathVariable Long productId,
            @Valid @RequestBody ProductVariantDTO variantDTO) {
        log.info("Request received to create variant for product ID: {}", productId);
        ProductVariantDTO createdVariant = productVariantService.createVariant(productId, variantDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() 
                .path("/{variantId}") 
                .buildAndExpand(createdVariant.getId())
                .toUri();

        log.info("Variant created successfully with ID: {} for product ID: {}", createdVariant.getId(), productId);
        return ResponseEntity.created(location).body(createdVariant);
    }

    @GetMapping
    public ResponseEntity<List<ProductVariantDTO>> getProductVariants(@PathVariable Long productId) {
        log.info("Request received to get variants for product ID: {}", productId);
        List<ProductVariantDTO> variants = productVariantService.getVariantsByProductId(productId);
        log.info("Found {} variants for product ID: {}", variants.size(), productId);
        return ResponseEntity.ok(variants);
    }

    @DeleteMapping("/{variantId}")
    public ResponseEntity<Void> deleteProductVariant(
            @PathVariable Long productId,
            @PathVariable Long variantId) {
        log.info("Request received to delete variant ID: {} for product ID: {}", variantId, productId);
        productVariantService.deleteVariant(productId, variantId);
        log.info("Variant ID: {} deleted successfully for product ID: {}", variantId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{variantId}")
    public ResponseEntity<ProductVariantDTO> getProductVariantById(
            @PathVariable Long productId,
            @PathVariable Long variantId) {
        log.info("Request received to get variant ID: {} for product ID: {}", variantId, productId);
        ProductVariantDTO variant = productVariantService.getVariantByIdAndProductId(productId, variantId);
        return ResponseEntity.ok(variant);
    }

    @PutMapping("/{variantId}")
    public ResponseEntity<ProductVariantDTO> updateProductVariant(
            @PathVariable Long productId,
            @PathVariable Long variantId,
            @Valid @RequestBody ProductVariantDTO variantDTO) {
        log.info("Request received to update variant ID: {} for product ID: {}", variantId, productId);
        variantDTO.setId(variantId);
        ProductVariantDTO updatedVariant = productVariantService.updateVariant(productId, variantId, variantDTO);
        return ResponseEntity.ok(updatedVariant);
    }

}