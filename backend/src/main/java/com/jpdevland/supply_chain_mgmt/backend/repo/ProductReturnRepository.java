package com.jpdevland.supply_chain_mgmt.backend.repo;

import com.jpdevland.supply_chain_mgmt.backend.model.ProductReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReturnRepository extends JpaRepository<ProductReturn, Long> {
    // Add custom query methods if needed, e.g., findByOrderId
}