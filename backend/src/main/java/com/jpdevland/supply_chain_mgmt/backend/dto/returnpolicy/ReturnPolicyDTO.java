package com.jpdevland.supply_chain_mgmt.backend.dto.returnpolicy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReturnPolicyDTO {
    private Long id;
    private Long productId;
    private int returnWindowDays;
    private boolean isReturnable;
}