package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.User; // Import User
import com.jpdevland.supply_chain_mgmt.backend.service.ProductService;
import com.jpdevland.supply_chain_mgmt.backend.utils.AppConstants; // For default pagination
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // Get logged in user
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // --- Public Endpoints ---

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "search", required = false) String searchTerm) {

        // Adjust page to be zero-based for Spring Data JPA
        Pageable pageable = PageRequest.of(page - 1, size, 
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<ProductDTO> products = productService.getAllProducts(pageable, searchTerm);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        ProductDTO product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    // TODO: Add public endpoints for variants, reviews etc.

    // --- Seller Endpoints ---

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody ProductCreateRequest createRequest,
            @AuthenticationPrincipal User sellerPrincipal // Get authenticated user (seller)
    ) {
        ProductDTO createdProduct = productService.createProduct(createRequest, sellerPrincipal.getId());
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductCreateRequest updateRequest,
            @AuthenticationPrincipal User sellerPrincipal) {
        // Service layer should verify if the sellerPrincipal.getId() matches product's seller
        ProductDTO updatedProduct = productService.updateProduct(productId, updateRequest, sellerPrincipal.getId());
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ADMIN')") // Seller or Admin can delete
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal User principal) {
        // Service needs to know *which* user is deleting for authorization
        productService.deleteProduct(productId, principal.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/seller/my-products")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<List<ProductDTO>> getMyProducts(
            @AuthenticationPrincipal User sellerPrincipal) {
        List<ProductDTO> products = productService.getProductsBySeller(sellerPrincipal.getId());
        return ResponseEntity.ok(products);
    }


    // --- Admin Endpoints (Example) ---
    // Maybe an admin endpoint to view products by a specific seller
    @GetMapping("/seller/{sellerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDTO>> getProductsBySellerIdAdmin(@PathVariable Long sellerId) {
        List<ProductDTO> products = productService.getProductsBySeller(sellerId);
        return ResponseEntity.ok(products);
    }

}