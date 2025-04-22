package com.jpdevland.supply_chain_mgmt.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRecommendationDto {

    private Long id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotBlank(message = "Algorithm cannot be blank")
    @Size(max = 100, message = "Algorithm name must be less than 100 characters")
    private String algorithm;

    @NotNull(message = "Score cannot be null")
    @DecimalMin(value = "0.0", message = "Score must be non-negative")
    @DecimalMax(value = "1.0", message = "Score cannot exceed 1.0") // Assuming score is between 0 and 1
    private BigDecimal score;

    private OffsetDateTime recommendedAt;
}
