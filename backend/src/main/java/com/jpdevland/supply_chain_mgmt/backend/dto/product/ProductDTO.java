package com.jpdevland.supply_chain_mgmt.backend.dto.product;

import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ProductDTO { // Example Response DTO
    private Long id;
    private Long sellerId;
    private String sellerName; // Include seller name
    private String name;
    private String description;
    private BigDecimal basePrice;
    private boolean available;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<ProductVariantDTO> variants;
    // Add lists for discounts, policies, aiImages, etc., as needed
}