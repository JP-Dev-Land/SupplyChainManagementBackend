package com.jpdevland.supply_chain_mgmt.backend.dto.product;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class ProductDiscountDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String discountType;
    private BigDecimal discountValue;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
}