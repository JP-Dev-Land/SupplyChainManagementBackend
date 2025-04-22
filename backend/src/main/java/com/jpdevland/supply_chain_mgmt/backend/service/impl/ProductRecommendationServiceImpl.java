package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.ProductRecommendationDto;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductRecommendation;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRecommendationRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.UserRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // Automatically creates constructor for final fields
public class ProductRecommendationServiceImpl implements ProductRecommendationService {

    private final ProductRecommendationRepository recommendationRepository;
    private final UserRepository userRepository; // To validate User exists
    private final ProductRepository productRepository; // To validate Product exists

    // --- Mapper Methods (Manual Mapping) ---
    // Consider using MapStruct for more complex scenarios

    private ProductRecommendationDto mapToDto(ProductRecommendation entity) {
        if (entity == null) return null;
        return ProductRecommendationDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .algorithm(entity.getAlgorithm())
                .score(entity.getScore())
                .recommendedAt(entity.getRecommendedAt())
                .build();
    }

    private ProductRecommendation mapToEntity(ProductRecommendationDto dto, User user, Product product) {
        if (dto == null) return null;
        // For creation, we don't set the ID. For update, we might fetch existing first.
        return ProductRecommendation.builder()
                .user(user)
                .product(product)
                .algorithm(dto.getAlgorithm())
                .score(dto.getScore())
                // recommendedAt is usually set by DB default or on creation
                .build();
    }

    private void updateEntityFromDto(ProductRecommendation entity, ProductRecommendationDto dto, User user, Product product) {
        entity.setUser(user);
        entity.setProduct(product);
        entity.setAlgorithm(dto.getAlgorithm());
        entity.setScore(dto.getScore());
        // recommendedAt typically shouldn't be updated manually unless required
    }

    // --- Service Methods Implementation ---

    @Override
    @Transactional(readOnly = true)
    public Page<ProductRecommendationDto> getAllRecommendations(Pageable pageable) {
        return recommendationRepository.findAll(pageable).map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductRecommendationDto getRecommendationById(Long id) {
        ProductRecommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductRecommendation", "id", id));
        return mapToDto(recommendation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductRecommendationDto> getRecommendationsByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return recommendationRepository.findByUserId(userId, pageable).map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductRecommendationDto> getRecommendationsByProductId(Long productId, Pageable pageable) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product", "id", productId);
        }
        return recommendationRepository.findByProductId(productId, pageable).map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductRecommendationDto> getRecommendationsByUserIdAndAlgorithm(Long userId, String algorithm, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return recommendationRepository.findByUserIdAndAlgorithm(userId, algorithm, pageable).map(this::mapToDto);
    }


    @Override
    @Transactional
    public ProductRecommendationDto createRecommendation(ProductRecommendationDto recommendationDto) {
        User user = userRepository.findById(recommendationDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recommendationDto.getUserId()));
        Product product = productRepository.findById(recommendationDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", recommendationDto.getProductId()));

        // Optional: Check if this specific recommendation already exists
        recommendationRepository.findByUserIdAndProductIdAndAlgorithm(
                        user.getId(), product.getId(), recommendationDto.getAlgorithm())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(String.format(
                            "Recommendation already exists for user %d, product %d, algorithm %s",
                            user.getId(), product.getId(), recommendationDto.getAlgorithm()));
                });


        ProductRecommendation newRecommendation = mapToEntity(recommendationDto, user, product);
        // Ensure recommendedAt is set if not handled by DB default/entity listener
        // newRecommendation.setRecommendedAt(OffsetDateTime.now()); // Uncomment if needed
        ProductRecommendation savedRecommendation = recommendationRepository.save(newRecommendation);
        return mapToDto(savedRecommendation);
    }

    @Override
    @Transactional
    public ProductRecommendationDto updateRecommendation(Long id, ProductRecommendationDto recommendationDto) {
        ProductRecommendation existingRecommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductRecommendation", "id", id));

        User user = userRepository.findById(recommendationDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recommendationDto.getUserId()));
        Product product = productRepository.findById(recommendationDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", recommendationDto.getProductId()));

        // Optional: Check for conflicts if the unique combination changes
        if (!existingRecommendation.getUser().getId().equals(user.getId()) ||
                !existingRecommendation.getProduct().getId().equals(product.getId()) ||
                !existingRecommendation.getAlgorithm().equals(recommendationDto.getAlgorithm())) {

            recommendationRepository.findByUserIdAndProductIdAndAlgorithm(
                            user.getId(), product.getId(), recommendationDto.getAlgorithm())
                    .ifPresent(conflict -> {
                        if (!conflict.getId().equals(id)) { // Ensure it's not conflicting with itself
                            throw new IllegalArgumentException(String.format(
                                    "Another recommendation already exists for user %d, product %d, algorithm %s",
                                    user.getId(), product.getId(), recommendationDto.getAlgorithm()));
                        }
                    });
        }

        updateEntityFromDto(existingRecommendation, recommendationDto, user, product);
        ProductRecommendation updatedRecommendation = recommendationRepository.save(existingRecommendation);
        return mapToDto(updatedRecommendation);
    }

    @Override
    @Transactional
    public void deleteRecommendation(Long id) {
        if (!recommendationRepository.existsById(id)) {
            throw new ResourceNotFoundException("ProductRecommendation", "id", id);
        }
        recommendationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteRecommendation(Long userId, Long productId, String algorithm) {
        ProductRecommendation recommendation = recommendationRepository.findByUserIdAndProductIdAndAlgorithm(userId, productId, algorithm)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProductRecommendation", "user " + userId + " product and algorithm ", (productId + " and " + algorithm)));
        recommendationRepository.delete(recommendation);
    }
}
