package com.jpdevland.supply_chain_mgmt.backend.dto.productreturn;

import lombok.Data;
import java.time.OffsetDateTime;

import com.jpdevland.supply_chain_mgmt.backend.enums.ReturnStatus;

@Data
public class ProductReturnDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private String reason;
    private ReturnStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}