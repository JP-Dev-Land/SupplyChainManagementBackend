
package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.UserRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductService;
import com.jpdevland.supply_chain_mgmt.backend.utils.DtoMapper; // Assume a mapper utility exists
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository; // To fetch seller
    private final DtoMapper dtoMapper;

    @Override
    @Transactional
    public ProductDTO createProduct(ProductCreateRequest createRequest, Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", sellerId));

        // Basic mapping (Refine with variants, etc.)
        Product product = Product.builder()
                .seller(seller)
                .name(createRequest.getName())
                .description(createRequest.getDescription())
                .basePrice(createRequest.getBasePrice())
                .available(createRequest.getAvailable())
                .build();
        // TODO: Map and associate variants if provided in createRequest

        Product savedProduct = productRepository.save(product);
        log.info("Product '{}' created with ID: {}", savedProduct.getName(), savedProduct.getId());
        return dtoMapper.toProductDTO(savedProduct); // Map to DTO
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        // TODO: Potentially load variants, active discounts eagerly or map lazily in DTO
        return dtoMapper.toProductDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable, String searchTerm) {
        Page<Product> productPage;
        if (searchTerm != null && !searchTerm.isBlank()) {
            // TODO: Implement proper search logic (e.g., across name, description)
            // This is a simple name search example:
            productPage = productRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }
        log.debug("Fetching products page {} size {} with term '{}'", pageable.getPageNumber(), pageable.getPageSize(), searchTerm);
        return productPage.map(dtoMapper::toProductDTO); // Map page content to DTOs
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsBySeller(Long sellerId) {
        if (!userRepository.existsById(sellerId)) {
            throw new ResourceNotFoundException("User", "id", sellerId);
        }
        List<Product> products = productRepository.findBySellerId(sellerId);
        log.debug("Found {} products for seller ID: {}", products.size(), sellerId);
        return products.stream()
                .map(dtoMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long productId, ProductCreateRequest updateRequest, Long sellerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        // Authorization check: Ensure the user updating is the seller or an admin
        if (!product.getSeller().getId().equals(sellerId)) {
            // Throw AccessDeniedException or handle appropriately - Spring Security @PreAuthorize is better
            throw new org.springframework.security.access.AccessDeniedException("User not authorized to update this product");
        }

        // Update fields
        product.setName(updateRequest.getName());
        product.setDescription(updateRequest.getDescription());
        product.setBasePrice(updateRequest.getBasePrice());
        product.setAvailable(updateRequest.getAvailable());
        // TODO: Implement logic to update variants (add new, update existing, remove old)

        Product updatedProduct = productRepository.save(product);
        log.info("Product ID: {} updated by seller ID: {}", productId, sellerId);
        return dtoMapper.toProductDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId, Long sellerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        // Authorization check
        if (!product.getSeller().getId().equals(sellerId)) {
            // Throw AccessDeniedException or handle appropriately
            throw new org.springframework.security.access.AccessDeniedException("User not authorized to delete this product");
        }

        productRepository.delete(product);
        log.info("Product ID: {} deleted by seller ID: {}", productId, sellerId);
    }

    // Implement other methods...
}