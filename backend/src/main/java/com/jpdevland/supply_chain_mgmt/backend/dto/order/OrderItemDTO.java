package com.jpdevland.supply_chain_mgmt.backend.dto.order;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long id;
    private Long productVariantId;
    private String variantName;
    private String productName;
    private int quantity;
    private BigDecimal priceAtOrder;
}