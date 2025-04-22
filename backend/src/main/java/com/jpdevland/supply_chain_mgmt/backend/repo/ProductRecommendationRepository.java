package com.jpdevland.supply_chain_mgmt.backend.repo;

import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductRecommendation;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRecommendationRepository extends JpaRepository<ProductRecommendation, Long> {

    // Find recommendations for a specific user
    List<ProductRecommendation> findByUser(User user);
    Page<ProductRecommendation> findByUserId(Long userId, Pageable pageable);

    // Find recommendations for a specific product
    List<ProductRecommendation> findByProduct(Product product);
    Page<ProductRecommendation> findByProductId(Long productId, Pageable pageable);

    // Find recommendations by user and algorithm
    List<ProductRecommendation> findByUserAndAlgorithm(User user, String algorithm);
    Page<ProductRecommendation> findByUserIdAndAlgorithm(Long userId, String algorithm, Pageable pageable);

    // Find a specific recommendation entry
    Optional<ProductRecommendation> findByUserIdAndProductIdAndAlgorithm(Long userId, Long productId, String algorithm);

    // Optionally: Find top N recommendations for a user ordered by score
    List<ProductRecommendation> findTopNByUserOrderByScoreDesc(User user, Pageable pageable); // Use Pageable with size N
    Page<ProductRecommendation> findByUserIdOrderByScoreDesc(Long userId, Pageable pageable);
}