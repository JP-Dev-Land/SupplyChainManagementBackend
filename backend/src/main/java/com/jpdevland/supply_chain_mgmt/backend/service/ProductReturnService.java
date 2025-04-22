package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.productreturn.ProductReturnDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.productreturn.ProductReturnRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.enums.ReturnStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductReturnService {
    ProductReturnDTO createProductReturn(ProductReturnRequestDTO requestDTO);
    ProductReturnDTO getProductReturnById(Long returnId);
    Page<ProductReturnDTO> getAllProductReturns(Pageable pageable);
    ProductReturnDTO updateProductReturnStatus(Long returnId, ReturnStatus status);
}