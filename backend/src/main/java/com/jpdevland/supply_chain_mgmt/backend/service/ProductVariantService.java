package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductVariantDTO;

import java.util.List;

public interface ProductVariantService {

    ProductVariantDTO createVariant(Long productId, ProductVariantDTO variantDTO);

    List<ProductVariantDTO> getVariantsByProductId(Long productId);

    void deleteVariant(Long productId, Long variantId);

    ProductVariantDTO getVariantByIdAndProductId(Long productId, Long variantId);

    ProductVariantDTO updateVariant(Long productId, Long variantId, ProductVariantDTO variantDTO);
}