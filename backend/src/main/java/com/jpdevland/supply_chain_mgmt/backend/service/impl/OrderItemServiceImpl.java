package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderItemCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderItemDTO;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.mapper.OrderItemMapper;
import com.jpdevland.supply_chain_mgmt.backend.model.OrderItem;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductVariant;
import com.jpdevland.supply_chain_mgmt.backend.repo.OrderItemRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductVariantRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderItemDTO createOrderItem(OrderItemCreateRequest request) {
        ProductVariant productVariant = productVariantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new ResourceNotFoundException("ProductVariant", "id", request.getProductVariantId()));

        OrderItem orderItem = OrderItem.builder()
                .productVariant(productVariant)
                .quantity(request.getQuantity())
                .priceAtOrder(request.getPriceAtOrder())
                .build();

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toDto(savedOrderItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemDTO> getAllOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findAll().stream()
                .filter(orderItem -> orderItem.getOrder().getId().equals(orderId))
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", orderItemId));
        orderItemRepository.delete(orderItem);
    }
}