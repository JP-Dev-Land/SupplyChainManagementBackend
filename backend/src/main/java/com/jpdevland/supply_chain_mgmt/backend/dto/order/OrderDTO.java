package com.jpdevland.supply_chain_mgmt.backend.dto.order;

import com.jpdevland.supply_chain_mgmt.backend.enums.OrderStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class OrderDTO { // Example Response DTO
    private Long id;
    private Long customerId;
    private String customerName;
    private Long sellerId;
    private String sellerName;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
    private String deliveryCity;
    private Double deliveryLatitude;
    private Double deliveryLongitude;
    private String deliveryPostalCode;
    private String deliveryStreet;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<OrderItemDTO> orderItems;
    // Could include payment status summary
}