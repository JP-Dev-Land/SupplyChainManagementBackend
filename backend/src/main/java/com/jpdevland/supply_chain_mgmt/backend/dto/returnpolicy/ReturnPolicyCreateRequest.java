package com.jpdevland.supply_chain_mgmt.backend.dto.returnpolicy;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReturnPolicyCreateRequest {
    @NotNull
    private Long productId;

    @Min(1)
    private int returnWindowDays;

    private boolean isReturnable = true;
}