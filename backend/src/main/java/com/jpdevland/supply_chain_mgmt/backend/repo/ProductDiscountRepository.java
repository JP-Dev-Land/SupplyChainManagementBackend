package com.jpdevland.supply_chain_mgmt.backend.repo;

import com.jpdevland.supply_chain_mgmt.backend.model.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, Long> {
    // Find discounts currently active for a specific product
    @Query("SELECT d FROM ProductDiscount d WHERE d.product.id = :productId " +
            "AND d.startDate <= :now AND d.endDate >= :now")
    List<ProductDiscount> findActiveDiscountsForProduct(@Param("productId") Long productId, @Param("now") OffsetDateTime now);

    List<ProductDiscount> findByProductId(Long productId);
}