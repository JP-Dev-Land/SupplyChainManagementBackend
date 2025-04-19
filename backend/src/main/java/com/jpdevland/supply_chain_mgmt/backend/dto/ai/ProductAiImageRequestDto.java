package com.jpdevland.supply_chain_mgmt.backend.dto.ai;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAiImageRequestDto {
    private Long productId;
    private String imageUrl;
}
