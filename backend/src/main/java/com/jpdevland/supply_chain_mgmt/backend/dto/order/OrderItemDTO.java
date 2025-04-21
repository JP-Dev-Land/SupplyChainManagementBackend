package com.jpdevland.supply_chain_mgmt.backend.dto.order;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long id;
    private Long productVariantId;
    private String productName; // Include product details
    private String variantName;
    private Integer quantity;
    private BigDecimal priceAtOrder;
}