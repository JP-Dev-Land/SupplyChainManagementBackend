package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDTO createOrder(OrderCreateRequest request, Long customerId);
    OrderDTO getOrderById(Long orderId);
    Page<OrderDTO> getOrdersByCustomer(Long customerId, Pageable pageable);
    Page<OrderDTO> getOrdersBySeller(Long sellerId, Pageable pageable);
}