package com.jpdevland.supply_chain_mgmt.backend.dto.ai;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAiImageResponseDto {
    private Long id;
    private Long productId;
    private String imageUrl;
    private String generatedAt;
}