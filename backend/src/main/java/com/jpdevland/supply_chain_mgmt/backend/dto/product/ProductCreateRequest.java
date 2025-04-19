package com.jpdevland.supply_chain_mgmt.backend.dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductCreateRequest {
    @NotBlank @Size(max = 255)
    private String name;
    private String description;
    @NotNull @DecimalMin("0.01") @Digits(integer=8, fraction=2)
    private BigDecimal basePrice;
    private Boolean available = true;
    private List<ProductVariantDTO> variants; // Allow creating variants initially
    // Add fields for initial Discount, Policy, Availability if needed
}
