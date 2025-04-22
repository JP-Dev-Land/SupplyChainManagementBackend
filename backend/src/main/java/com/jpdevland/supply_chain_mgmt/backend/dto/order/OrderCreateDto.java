package com.jpdevland.supply_chain_mgmt.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid; // Important for nested validation
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateDto {

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    // Seller might be derived from products, or specified if order is seller-specific
    @NotNull(message = "Seller ID cannot be null")
    private Long sellerId;

    // Delivery details
    @Size(max = 255, message = "Delivery city exceeds maximum length")
    private String deliveryCity;
    private Double deliveryLatitude;
    private Double deliveryLongitude;
    @Size(max = 50, message = "Delivery postal code exceeds maximum length")
    private String deliveryPostalCode;
    @Size(max = 255, message = "Delivery street exceeds maximum length")
    private String deliveryStreet;

    @NotEmpty(message = "Order must contain at least one item")
    @Valid // Enable validation on items in the list
    private List<OrderItemCreateDto> items;

    // Status and TotalAmount are typically set by the service
    // private OrderStatus orderStatus = OrderStatus.PENDING;
    // private BigDecimal totalAmount;
}
