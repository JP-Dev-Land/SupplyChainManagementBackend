package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.returnpolicy.ReturnPolicyCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.returnpolicy.ReturnPolicyDTO;

import java.util.List;

public interface ReturnPolicyService {

    ReturnPolicyDTO createReturnPolicy(ReturnPolicyCreateRequest request);

    List<ReturnPolicyDTO> getAllReturnPoliciesByProductId(Long productId);

    void deleteReturnPolicy(Long policyId);
}