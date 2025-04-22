package com.jpdevland.supply_chain_mgmt.backend.dto.productreturn;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ProductReturnRequestDTO {
    @NotNull
    private Long orderId;

    @NotNull
    private Long productId;

    @NotBlank
    private String reason;
}