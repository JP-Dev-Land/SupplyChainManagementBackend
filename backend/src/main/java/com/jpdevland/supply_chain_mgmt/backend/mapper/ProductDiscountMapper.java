package com.jpdevland.supply_chain_mgmt.backend.mapper;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDiscountDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductDiscount;
import org.springframework.stereotype.Component;

@Component
public class ProductDiscountMapper {

    public ProductDiscountDTO toDto(ProductDiscount discount) {
        return ProductDiscountDTO.builder()
                .id(discount.getId())
                .productId(discount.getProduct().getId())
                .productName(discount.getProduct().getName())
                .discountType(discount.getDiscountType().name())
                .discountValue(discount.getDiscountValue())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .build();
    }
}