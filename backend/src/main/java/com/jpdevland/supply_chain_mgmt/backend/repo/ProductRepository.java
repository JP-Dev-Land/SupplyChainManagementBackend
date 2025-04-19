package com.jpdevland.supply_chain_mgmt.backend.repo;

import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySellerId(Long sellerId);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
