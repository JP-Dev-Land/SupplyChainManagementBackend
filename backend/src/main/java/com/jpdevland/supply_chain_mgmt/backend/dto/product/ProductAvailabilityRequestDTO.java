package com.jpdevland.supply_chain_mgmt.backend.dto.product;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAvailabilityRequestDTO {
    private Long productId;
    private String region;
    private Boolean available;
}