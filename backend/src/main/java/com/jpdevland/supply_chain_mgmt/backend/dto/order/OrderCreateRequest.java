package com.jpdevland.supply_chain_mgmt.backend.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequest {
    // Customer ID is taken from authenticated user normally
    @NotNull
    private Long sellerId; // Need to know which seller's products are being ordered

    @NotEmpty
    @Valid
    private List<OrderItemRequest> items;

    // Delivery Address
    private String deliveryCity;
    private Double deliveryLatitude;
    private Double deliveryLongitude;
    private String deliveryPostalCode;
    private String deliveryStreet;
}