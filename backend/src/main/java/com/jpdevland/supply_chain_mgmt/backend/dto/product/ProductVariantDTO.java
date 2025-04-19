package com.jpdevland.supply_chain_mgmt.backend.dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductVariantDTO {
    private Long id; // Null for new variants
    @NotBlank @Size(max = 255)
    private String variantName;
    @NotNull @DecimalMin("0.00") @Digits(integer=8, fraction=2)
    private BigDecimal priceModifier = BigDecimal.ZERO;
    @NotNull @Min(0)
    private Integer stockQuantity = 0;
}
