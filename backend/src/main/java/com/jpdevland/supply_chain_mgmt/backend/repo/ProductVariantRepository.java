package com.jpdevland.supply_chain_mgmt.backend.repo;

import com.jpdevland.supply_chain_mgmt.backend.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByProductId(Long productId);
    Optional<ProductVariant> findByIdAndProductId(Long variantId, Long productId);
}