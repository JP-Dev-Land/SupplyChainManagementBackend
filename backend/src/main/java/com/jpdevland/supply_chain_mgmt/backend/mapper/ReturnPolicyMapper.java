package com.jpdevland.supply_chain_mgmt.backend.mapper;

import com.jpdevland.supply_chain_mgmt.backend.dto.returnpolicy.ReturnPolicyDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.ReturnPolicy;
import org.springframework.stereotype.Component;

@Component
public class ReturnPolicyMapper {

    public ReturnPolicyDTO toDto(ReturnPolicy returnPolicy) {
        return ReturnPolicyDTO.builder()
                .id(returnPolicy.getId())
                .productId(returnPolicy.getProduct().getId())
                .returnWindowDays(returnPolicy.getReturnWindowDays())
                .isReturnable(returnPolicy.isReturnable())
                .build();
    }
}