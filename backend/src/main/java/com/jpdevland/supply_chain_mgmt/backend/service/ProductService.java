package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductCreateRequest createRequest, Long sellerId);
    ProductDTO getProductById(Long productId);
    Page<ProductDTO> getAllProducts(Pageable pageable, String searchTerm);
    List<ProductDTO> getProductsBySeller(Long sellerId);
    ProductDTO updateProduct(Long productId, ProductCreateRequest updateRequest, Long sellerId);
    void deleteProduct(Long productId, Long sellerId);
    // Add methods for managing variants, discounts, availability etc.
}