package com.jpdevland.supply_chain_mgmt.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

// Used within OrderCreateDto
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemCreateDto {

    @NotNull(message = "Product Variant ID cannot be null")
    private Long productVariantId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
