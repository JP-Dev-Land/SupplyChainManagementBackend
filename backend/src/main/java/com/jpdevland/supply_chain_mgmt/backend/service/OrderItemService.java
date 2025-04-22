package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderItemCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderItemDTO;

import java.util.List;

public interface OrderItemService {

    OrderItemDTO createOrderItem(OrderItemCreateRequest request);

    List<OrderItemDTO> getAllOrderItemsByOrderId(Long orderId);

    void deleteOrderItem(Long orderItemId);
}