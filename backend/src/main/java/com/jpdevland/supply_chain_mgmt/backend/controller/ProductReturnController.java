package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.productreturn.ProductReturnDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.productreturn.ProductReturnRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.enums.ReturnStatus;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-returns")
@RequiredArgsConstructor
public class ProductReturnController {

    private final ProductReturnService productReturnService;

    @PostMapping
    public ResponseEntity<ProductReturnDTO> createProductReturn(@RequestBody ProductReturnRequestDTO requestDTO) {
        ProductReturnDTO createdReturn = productReturnService.createProductReturn(requestDTO);
        return new ResponseEntity<>(createdReturn, HttpStatus.CREATED);
    }

    @GetMapping("/{returnId}")
    public ResponseEntity<ProductReturnDTO> getProductReturnById(@PathVariable Long returnId) {
        ProductReturnDTO productReturn = productReturnService.getProductReturnById(returnId);
        return ResponseEntity.ok(productReturn);
    }

    @GetMapping
    public ResponseEntity<Page<ProductReturnDTO>> getAllProductReturns(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "DESC") String sortDir) {
        Pageable pageable = PageRequest.of(page, size, 
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<ProductReturnDTO> productReturns = productReturnService.getAllProductReturns(pageable);
        return ResponseEntity.ok(productReturns);
    }

    @PatchMapping("/{returnId}/status")
    public ResponseEntity<ProductReturnDTO> updateProductReturnStatus(
            @PathVariable Long returnId, 
            @RequestParam ReturnStatus status) {
        ProductReturnDTO updatedReturn = productReturnService.updateProductReturnStatus(returnId, status);
        return ResponseEntity.ok(updatedReturn);
    }
}