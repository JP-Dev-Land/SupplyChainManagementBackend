package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDiscountCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDiscountDTO;
import com.jpdevland.supply_chain_mgmt.backend.enums.DiscountType;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.mapper.ProductDiscountMapper;
import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductDiscount;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductDiscountRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDiscountServiceImpl implements ProductDiscountService {

    private final ProductDiscountRepository productDiscountRepository;
    private final ProductRepository productRepository;
    private final ProductDiscountMapper productDiscountMapper;

    @Transactional
    @Override
    public ProductDiscountDTO createProductDiscount(ProductDiscountCreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId()));

        ProductDiscount discount = ProductDiscount.builder()
                .product(product)
                .discountType(DiscountType.valueOf(request.getDiscountType().toUpperCase()))
                .discountValue(request.getDiscountValue())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        ProductDiscount savedDiscount = productDiscountRepository.save(discount);

        return productDiscountMapper.toDto(savedDiscount);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDiscountDTO> getAllDiscountsByProductId(Long productId) {
        return productDiscountRepository.findAll().stream()
                .filter(discount -> discount.getProduct().getId().equals(productId))
                .map(productDiscountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteProductDiscount(Long discountId) {
        ProductDiscount discount = productDiscountRepository.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductDiscount", "id", discountId));
        productDiscountRepository.delete(discount);
    }
}