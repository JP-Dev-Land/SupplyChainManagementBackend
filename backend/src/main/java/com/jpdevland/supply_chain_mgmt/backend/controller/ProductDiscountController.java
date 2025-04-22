package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDiscountCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDiscountDTO;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductDiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-discounts")
@RequiredArgsConstructor
public class ProductDiscountController {

    private final ProductDiscountService productDiscountService;

    @PostMapping
    public ResponseEntity<ProductDiscountDTO> createProductDiscount(@Valid @RequestBody ProductDiscountCreateRequest request) {
        ProductDiscountDTO createdDiscount = productDiscountService.createProductDiscount(request);
        return new ResponseEntity<>(createdDiscount, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductDiscountDTO>> getDiscountsByProductId(@PathVariable Long productId) {
        List<ProductDiscountDTO> discounts = productDiscountService.getAllDiscountsByProductId(productId);
        return ResponseEntity.ok(discounts);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteProductDiscount(@PathVariable Long discountId) {
        productDiscountService.deleteProductDiscount(discountId);
        return ResponseEntity.noContent().build();
    }
}