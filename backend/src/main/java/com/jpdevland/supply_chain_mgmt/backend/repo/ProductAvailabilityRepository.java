package com.jpdevland.supply_chain_mgmt.backend.repo;

import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductAvailabilityRepository extends JpaRepository<ProductAvailability, Long> {

    Optional<ProductAvailability> findByProductAndRegion(Product product, String region);
    Optional<ProductAvailability> findByProductIdAndRegion(Long productId, String region);

    List<ProductAvailability> findByProduct(Product product);

    List<ProductAvailability> findByRegion(String region);

    List<ProductAvailability> findByAvailableTrue();

    boolean existsByProductAndRegion(Product product, String region);
}
