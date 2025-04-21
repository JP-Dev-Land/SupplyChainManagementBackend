package com.jpdevland.supply_chain_mgmt.backend.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {
    @NotNull
    private Long productVariantId;
    @NotNull @Min(1)
    private Integer quantity;
}

