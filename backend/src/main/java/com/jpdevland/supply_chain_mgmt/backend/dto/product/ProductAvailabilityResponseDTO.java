package com.jpdevland.supply_chain_mgmt.backend.dto.product;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAvailabilityResponseDTO {
    private Long id;
    private Long productId;
    private String region;
    private Boolean available;
    private OffsetDateTime updatedAt;
}
