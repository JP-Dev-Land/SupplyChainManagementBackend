package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDiscountCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDiscountDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductDiscountService {
    @Transactional
    ProductDiscountDTO createProductDiscount(ProductDiscountCreateRequest request);

    @Transactional(readOnly = true)
    List<ProductDiscountDTO> getAllDiscountsByProductId(Long productId);

    @Transactional
    void deleteProductDiscount(Long discountId);
}
