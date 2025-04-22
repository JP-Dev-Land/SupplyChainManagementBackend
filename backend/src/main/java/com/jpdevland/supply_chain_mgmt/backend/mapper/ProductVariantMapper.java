package com.jpdevland.supply_chain_mgmt.backend.mapper;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductVariantDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductVariant;
import org.springframework.stereotype.Component;

@Component
public class ProductVariantMapper {

    public ProductVariantDTO toDto(ProductVariant variant) {
        ProductVariantDTO dto = new ProductVariantDTO();
        dto.setId(variant.getId());
        dto.setVariantName(variant.getVariantName());
        dto.setPriceModifier(variant.getPriceModifier());
        dto.setStockQuantity(variant.getStockQuantity());
        return dto;
    }

    public ProductVariant toEntity(ProductVariantDTO dto) {
        return ProductVariant.builder()
                .variantName(dto.getVariantName())
                .priceModifier(dto.getPriceModifier())
                .stockQuantity(dto.getStockQuantity())
                .build();
    }
}