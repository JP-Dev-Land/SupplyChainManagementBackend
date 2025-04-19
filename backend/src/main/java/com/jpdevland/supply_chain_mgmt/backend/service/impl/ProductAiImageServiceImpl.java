package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.ai.ProductAiImageRequestDto;
import com.jpdevland.supply_chain_mgmt.backend.dto.ai.ProductAiImageResponseDto;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductAiImage;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductAiImageRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductAiImageService;
import com.jpdevland.supply_chain_mgmt.backend.mapper.ProductAiImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductAiImageServiceImpl implements ProductAiImageService {

    private final ProductAiImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductAiImageResponseDto create(ProductAiImageRequestDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", dto.getProductId()));

        ProductAiImage image = ProductAiImage.builder()
                .product(product)
                .imageUrl(dto.getImageUrl())
                .build();

        return ProductAiImageMapper.toDto(imageRepository.save(image));
    }

    @Override
    public List<ProductAiImageResponseDto> getByProductId(Long productId) {
        return imageRepository.findByProductId(productId).stream()
                .map(ProductAiImageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        ProductAiImage image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductAiImage", "id", id));
        imageRepository.delete(image);
    }
}