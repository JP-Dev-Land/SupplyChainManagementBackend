package com.jpdevland.supply_chain_mgmt.backend.dto.ai;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAiImageRequestDto {
    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotBlank(message = "Image URL cannot be blank")
    private String imageUrl;
}
