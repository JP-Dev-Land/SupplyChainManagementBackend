package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderItemRequest;
import com.jpdevland.supply_chain_mgmt.backend.enums.OrderStatus;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.model.Order;
import com.jpdevland.supply_chain_mgmt.backend.model.OrderItem;
import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductVariant;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import com.jpdevland.supply_chain_mgmt.backend.repo.OrderRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductVariantRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.UserRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.OrderService;
import com.jpdevland.supply_chain_mgmt.backend.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final DtoMapper dtoMapper;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderCreateRequest request, Long customerId) {
        log.info("Creating order for customerId: {}", customerId);

        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer User", "id", customerId));
        User seller = userRepository.findById(request.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller User", "id", request.getSellerId()));

        Order order = Order.builder()
                .customer(customer)
                .seller(seller)
                .orderStatus(OrderStatus.PENDING)
                .deliveryCity(request.getDeliveryCity())
                .deliveryLatitude(request.getDeliveryLatitude())
                .deliveryLongitude(request.getDeliveryLongitude())
                .deliveryPostalCode(request.getDeliveryPostalCode())
                .deliveryStreet(request.getDeliveryStreet())
                .orderItems(new ArrayList<>())
                .payments(new HashSet<>())
                .trackingUpdates(new HashSet<>())
                .build();

        BigDecimal totalOrderAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Long variantId = itemRequest.getProductVariantId();
            Integer quantity = itemRequest.getQuantity();
            Long productId = itemRequest.getProductId();
            log.info("Processing item: ProductId={}, VariantId={}, Quantity={}", productId, variantId, quantity);


            if (quantity == null || quantity <= 0) {
                log.warn("Skipping order item with invalid quantity for variantId {}: {}", variantId, quantity);
                continue;
            }

            ProductVariant variant = productVariantRepository.findByIdAndProductId(variantId, productId)
                     .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("ProductVariant with id %d for product %d", variantId, productId),
                            "id/productId", variantId + "/" + productId));

            Product product = variant.getProduct();
            if (product == null) {
                log.error("Data inconsistency: Product is null for ProductVariant with id: {}", variantId);
                throw new IllegalStateException("Data inconsistency: Product missing for variant " + variantId);
            }
            if (!product.getId().equals(productId)) {
                log.error("Data inconsistency: Fetched variant {} (product {}) does not match requested product {}", variantId, product.getId(), productId);
                throw new IllegalStateException("Data inconsistency: Variant does not belong to the specified product.");
            }


            BigDecimal unitPrice = product.getBasePrice().add(variant.getPriceModifier());
            BigDecimal itemTotalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
            totalOrderAmount = totalOrderAmount.add(itemTotalPrice);

            OrderItem orderItem = OrderItem.builder()
                    .productVariant(variant)
                    .quantity(quantity)
                    .priceAtOrder(itemTotalPrice)
                    .build();

            order.addOrderItem(orderItem);

            log.debug("Added order item: VariantId={}, Quantity={}, UnitPrice={}, ItemTotal={}",
                      variantId, quantity, unitPrice, itemTotalPrice);
        }

        if (order.getOrderItems().isEmpty()) {
            log.error("Attempted to create an order with no valid items for customerId: {}", customerId);
            throw new IllegalArgumentException("Cannot create an order with no valid items.");
        }
        order.setTotalAmount(totalOrderAmount);

        log.info("Saving order for customerId: {} with {} items and total amount: {}",
                 customerId, order.getOrderItems().size(), totalOrderAmount);
        Order savedOrder = orderRepository.save(order);
        log.info("Successfully saved order with ID: {}", savedOrder.getId());

        return dtoMapper.toOrderDTO(savedOrder);
    }


    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long orderId) {
        log.debug("Fetching order by id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        return dtoMapper.toOrderDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersByCustomer(Long customerId, Pageable pageable) {
         log.debug("Fetching orders for customerId: {} with pageable: {}", customerId, pageable);
        if (userRepository.existsById(customerId)) {
            return orderRepository.findByCustomerId(customerId, pageable)
                    .map(dtoMapper::toOrderDTO);
        } else {
             log.warn("Attempted to fetch orders for non-existent customerId: {}", customerId);
             throw new ResourceNotFoundException("Customer User", "id", customerId);
            // Or return Page.empty(); depending on requirements
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersBySeller(Long sellerId, Pageable pageable) {
         log.debug("Fetching orders for sellerId: {} with pageable: {}", sellerId, pageable);
         if (userRepository.existsById(sellerId)) { // Optional: Check if seller exists first
            return orderRepository.findBySellerId(sellerId, pageable)
                    .map(dtoMapper::toOrderDTO);
        } else {
             log.warn("Attempted to fetch orders for non-existent sellerId: {}", sellerId);
             throw new ResourceNotFoundException("Seller User", "id", sellerId);
             // Or return Page.empty();
         }
    }
}