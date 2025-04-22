package com.jpdevland.supply_chain_mgmt.backend.repo;

import com.jpdevland.supply_chain_mgmt.backend.enums.OrderStatus;
import com.jpdevland.supply_chain_mgmt.backend.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find orders by customer ID
    Page<Order> findByCustomerId(Long customerId, Pageable pageable);
    List<Order> findByCustomerId(Long customerId); // Non-paginated version if needed

    // Find orders by seller ID
    Page<Order> findBySellerId(Long sellerId, Pageable pageable);
    List<Order> findBySellerId(Long sellerId);

    // Find orders by status
    Page<Order> findByOrderStatus(OrderStatus status, Pageable pageable);
    List<Order> findByOrderStatus(OrderStatus status);

    // Find orders by customer and status
    Page<Order> findByCustomerIdAndOrderStatus(Long customerId, OrderStatus status, Pageable pageable);

    // Find orders within a date range
    Page<Order> findByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end, Pageable pageable);

    // Example of a more complex query using JPQL (e.g., fetch associated items eagerly)
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.id = :id")
    Order findByIdWithItems(@Param("id") Long id);

    // Find orders by delivery city (example)
    Page<Order> findByDeliveryCityIgnoreCase(String city, Pageable pageable);
}
