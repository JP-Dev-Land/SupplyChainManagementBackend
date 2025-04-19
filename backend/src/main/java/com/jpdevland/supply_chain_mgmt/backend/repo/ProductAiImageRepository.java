package com.jpdevland.supply_chain_mgmt.backend.repo;

import com.jpdevland.supply_chain_mgmt.backend.model.ProductAiImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductAiImageRepository extends JpaRepository<ProductAiImage, Long> {
    List<ProductAiImage> findByProductId(Long productId);
}