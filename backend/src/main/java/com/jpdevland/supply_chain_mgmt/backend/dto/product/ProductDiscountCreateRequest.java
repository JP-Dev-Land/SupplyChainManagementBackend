package com.jpdevland.supply_chain_mgmt.backend.dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class ProductDiscountCreateRequest {
    @NotNull
    private Long productId;

    @NotBlank
    private String discountType;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal discountValue;

    @NotNull
    private OffsetDateTime startDate;

    @NotNull
    private OffsetDateTime endDate;
}