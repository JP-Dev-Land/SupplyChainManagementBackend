package com.jpdevland.supply_chain_mgmt.backend.dto.order;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemCreateRequest {
    @NotNull
    private Long productVariantId;

    @Min(1)
    private int quantity;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal priceAtOrder;
}