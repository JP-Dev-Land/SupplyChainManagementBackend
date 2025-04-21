package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductAvailabilityRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductAvailabilityResponseDTO;

import java.util.List;

public interface ProductAvailabilityService {

    ProductAvailabilityResponseDTO createOrUpdate(ProductAvailabilityRequestDTO requestDTO);

    ProductAvailabilityResponseDTO getById(Long id);

    List<ProductAvailabilityResponseDTO> getAll();

    void delete(Long id);
}
