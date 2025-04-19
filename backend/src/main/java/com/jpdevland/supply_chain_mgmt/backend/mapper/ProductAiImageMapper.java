package com.jpdevland.supply_chain_mgmt.backend.mapper;

import com.jpdevland.supply_chain_mgmt.backend.dto.ai.ProductAiImageResponseDto;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductAiImage;

public class ProductAiImageMapper {
    public static ProductAiImageResponseDto toDto(ProductAiImage image) {
        return ProductAiImageResponseDto.builder()
                .id(image.getId())
                .productId(image.getProduct().getId())
                .imageUrl(image.getImageUrl())
                .generatedAt(image.getGeneratedAt().toString())
                .build();
    }
}