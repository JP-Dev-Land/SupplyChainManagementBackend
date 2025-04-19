package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.ai.ProductAiImageRequestDto;
import com.jpdevland.supply_chain_mgmt.backend.dto.ai.ProductAiImageResponseDto;

import java.util.List;

public interface ProductAiImageService {
    ProductAiImageResponseDto create(ProductAiImageRequestDto dto);
    List<ProductAiImageResponseDto> getByProductId(Long productId);
    void delete(Long id);
}