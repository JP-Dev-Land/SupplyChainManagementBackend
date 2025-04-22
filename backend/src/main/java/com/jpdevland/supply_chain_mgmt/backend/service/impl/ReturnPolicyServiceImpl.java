package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.returnpolicy.ReturnPolicyCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.returnpolicy.ReturnPolicyDTO;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.mapper.ReturnPolicyMapper;
import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.ReturnPolicy;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.ReturnPolicyRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.ReturnPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReturnPolicyServiceImpl implements ReturnPolicyService {

    private final ReturnPolicyRepository returnPolicyRepository;
    private final ProductRepository productRepository;
    private final ReturnPolicyMapper returnPolicyMapper;

    @Override
    @Transactional
    public ReturnPolicyDTO createReturnPolicy(ReturnPolicyCreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId()));

        ReturnPolicy returnPolicy = ReturnPolicy.builder()
                .product(product)
                .returnWindowDays(request.getReturnWindowDays())
                .isReturnable(request.isReturnable())
                .build();

        ReturnPolicy savedPolicy = returnPolicyRepository.save(returnPolicy);
        return returnPolicyMapper.toDto(savedPolicy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReturnPolicyDTO> getAllReturnPoliciesByProductId(Long productId) {
        return returnPolicyRepository.findAll().stream()
                .filter(policy -> policy.getProduct().getId().equals(productId))
                .map(returnPolicyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteReturnPolicy(Long policyId) {
        ReturnPolicy policy = returnPolicyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("ReturnPolicy", "id", policyId));
        returnPolicyRepository.delete(policy);
    }
}