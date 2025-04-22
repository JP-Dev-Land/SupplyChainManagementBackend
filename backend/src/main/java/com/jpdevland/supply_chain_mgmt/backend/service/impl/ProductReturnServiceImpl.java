package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.productreturn.ProductReturnDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.productreturn.ProductReturnRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.enums.ReturnStatus;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.model.ProductReturn;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductReturnRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductReturnService;
import com.jpdevland.supply_chain_mgmt.backend.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductReturnServiceImpl implements ProductReturnService {

    private final ProductReturnRepository productReturnRepository;
    private final DtoMapper dtoMapper;

    @Override
    @Transactional
    public ProductReturnDTO createProductReturn(ProductReturnRequestDTO requestDTO) {
        ProductReturn productReturn = ProductReturn.builder()
                .reason(requestDTO.getReason())
                .status(ReturnStatus.PENDING)
                .build();

        ProductReturn savedReturn = productReturnRepository.save(productReturn);
        return dtoMapper.toProductReturnDTO(savedReturn);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductReturnDTO getProductReturnById(Long returnId) {
        ProductReturn productReturn = productReturnRepository.findById(returnId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductReturn", "id", returnId));
        return dtoMapper.toProductReturnDTO(productReturn);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductReturnDTO> getAllProductReturns(Pageable pageable) {
        return productReturnRepository.findAll(pageable)
                .map(dtoMapper::toProductReturnDTO);
    }

    @Override
    @Transactional
    public ProductReturnDTO updateProductReturnStatus(Long returnId, ReturnStatus status) {
        ProductReturn productReturn = productReturnRepository.findById(returnId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductReturn", "id", returnId));

        productReturn.setStatus(status);
        ProductReturn updatedReturn = productReturnRepository.save(productReturn);
        return dtoMapper.toProductReturnDTO(updatedReturn);
    }
}