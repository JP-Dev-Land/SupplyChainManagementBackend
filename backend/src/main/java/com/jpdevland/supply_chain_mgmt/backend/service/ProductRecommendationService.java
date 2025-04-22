package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.ProductRecommendationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRecommendationService {

    Page<ProductRecommendationDto> getAllRecommendations(Pageable pageable);

    ProductRecommendationDto getRecommendationById(Long id);

    Page<ProductRecommendationDto> getRecommendationsByUserId(Long userId, Pageable pageable);

    Page<ProductRecommendationDto> getRecommendationsByProductId(Long productId, Pageable pageable);

    Page<ProductRecommendationDto> getRecommendationsByUserIdAndAlgorithm(Long userId, String algorithm, Pageable pageable);

    ProductRecommendationDto createRecommendation(ProductRecommendationDto recommendationDto);

    // Update might not be common for recommendations, but included for completeness
    ProductRecommendationDto updateRecommendation(Long id, ProductRecommendationDto recommendationDto);

    void deleteRecommendation(Long id);

    void deleteRecommendation(Long userId, Long productId, String algorithm);
}
