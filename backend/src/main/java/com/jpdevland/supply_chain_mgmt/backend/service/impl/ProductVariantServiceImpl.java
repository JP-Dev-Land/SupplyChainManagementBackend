package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductVariantDTO;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.mapper.ProductVariantMapper;
import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductVariant;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductVariantRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductVariantMapper productVariantMapper;

    @Override
    @Transactional
    public ProductVariantDTO createVariant(Long productId, ProductVariantDTO variantDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        ProductVariant variant = ProductVariant.builder()
                .product(product)
                .variantName(variantDTO.getVariantName())
                .priceModifier(variantDTO.getPriceModifier())
                .stockQuantity(variantDTO.getStockQuantity())
                .build();

        ProductVariant savedVariant = productVariantRepository.save(variant);
        return productVariantMapper.toDto(savedVariant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductVariantDTO> getVariantsByProductId(Long productId) {
        return productVariantRepository.findByProductId(productId).stream()
                .map(productVariantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductVariantDTO getVariantByIdAndProductId(Long productId, Long variantId) {
        // Keep the detailed message here as the condition involves both IDs
        ProductVariant variant = productVariantRepository.findByIdAndProductId(variantId, productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProductVariant with id " + variantId, "Product with id ", productId));
        return productVariantMapper.toDto(variant);
    }

    @Override
    @Transactional
    public ProductVariantDTO updateVariant(Long productId, Long variantId, ProductVariantDTO variantDTO) {
        ProductVariant existingVariant = productVariantRepository.findByIdAndProductId(variantId, productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProductVariant with id " + variantId, "Product with id ", productId));

        existingVariant.setVariantName(variantDTO.getVariantName());
        existingVariant.setPriceModifier(variantDTO.getPriceModifier());
        existingVariant.setStockQuantity(variantDTO.getStockQuantity());

        ProductVariant updatedVariant = productVariantRepository.save(existingVariant);
        return productVariantMapper.toDto(updatedVariant);
    }

    @Override
    @Transactional
    public void deleteVariant(Long productId, Long variantId) {
        ProductVariant variant = productVariantRepository.findByIdAndProductId(variantId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductVariant", "id", variantId));
        productVariantRepository.delete(variant);
    }
}