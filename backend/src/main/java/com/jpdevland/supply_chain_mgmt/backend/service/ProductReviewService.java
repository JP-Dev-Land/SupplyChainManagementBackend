package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductReviewService {
    ProductReviewDTO createReview(ProductReviewDTO reviewDTO);
    Page<ProductReviewDTO> getReviewsByProduct(Long productId, Pageable pageable);
    Page<ProductReviewDTO> getReviewsByCustomer(Long customerId, Pageable pageable);
}