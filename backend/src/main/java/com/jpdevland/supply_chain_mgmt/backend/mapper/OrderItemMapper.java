package com.jpdevland.supply_chain_mgmt.backend.mapper;

import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderItemDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemDTO toDto(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setProductVariantId(orderItem.getProductVariant().getId());
        dto.setVariantName(orderItem.getProductVariant().getVariantName());
        dto.setProductName(orderItem.getProductVariant().getProduct().getName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPriceAtOrder(orderItem.getPriceAtOrder());
        return dto;
    }
}