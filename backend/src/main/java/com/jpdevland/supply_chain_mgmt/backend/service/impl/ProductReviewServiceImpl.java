package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductReviewDTO;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductReview;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductReviewRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.UserRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductReviewService;
import com.jpdevland.supply_chain_mgmt.backend.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DtoMapper dtoMapper;

    @Override
    @Transactional
    public ProductReviewDTO createReview(ProductReviewDTO reviewDTO) {
        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", reviewDTO.getProductId()));

        User customer = userRepository.findById(reviewDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", reviewDTO.getCustomerId()));

        ProductReview review = ProductReview.builder()
                .product(product)
                .customer(customer)
                .rating(reviewDTO.getRating())
                .review(reviewDTO.getReview())
                .build();

        ProductReview savedReview = reviewRepository.save(review);
        return dtoMapper.toProductReviewDTO(savedReview);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductReviewDTO> getReviewsByProduct(Long productId, Pageable pageable) {
        return reviewRepository.findByProductId(productId, pageable)
                .map(dtoMapper::toProductReviewDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductReviewDTO> getReviewsByCustomer(Long customerId, Pageable pageable) {
        return reviewRepository.findByCustomerId(customerId, pageable)
                .map(dtoMapper::toProductReviewDTO);
    }
}