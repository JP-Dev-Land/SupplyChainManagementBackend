package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductVariantDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.UserRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductService;
import com.jpdevland.supply_chain_mgmt.backend.service.ProductVariantService;
import com.jpdevland.supply_chain_mgmt.backend.utils.DtoMapper;
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
    private final ProductVariantService productVariantService;

    @Override
    @Transactional
    public ProductDTO createProduct(ProductCreateRequest createRequest, Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", sellerId));

        Product product = Product.builder()
                .seller(seller)
                .name(createRequest.getName())
                .description(createRequest.getDescription())
                .basePrice(createRequest.getBasePrice())
                .available(createRequest.getAvailable())
                .build();

        Product savedProduct = productRepository.save(product);

        if (createRequest.getVariants() != null) {
            for (ProductVariantDTO variantDTO : createRequest.getVariants()) {
                productVariantService.createVariant(savedProduct.getId(), variantDTO);
            }
        }

        return dtoMapper.toProductDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        return dtoMapper.toProductDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable, String searchTerm) {
        Page<Product> productPage;
        if (searchTerm != null && !searchTerm.isBlank()) {
            productPage = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchTerm, searchTerm, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }
        log.debug("Fetching products page {} size {} with term '{}'", pageable.getPageNumber(), pageable.getPageSize(), searchTerm);
        log.debug("Total products fetched: {}", productPage.getTotalElements());
        return productPage.map(dtoMapper::toProductDTO);
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

        if (!product.getSeller().getId().equals(sellerId)) {
            throw new org.springframework.security.access.AccessDeniedException("User not authorized to update this product");
        }

        product.setName(updateRequest.getName());
        product.setDescription(updateRequest.getDescription());
        product.setBasePrice(updateRequest.getBasePrice());
        product.setAvailable(updateRequest.getAvailable());

        if (updateRequest.getVariants() != null) {
            List<ProductVariantDTO> existingVariants = productVariantService.getVariantsByProductId(productId);

            for (ProductVariantDTO variantDTO : updateRequest.getVariants()) {
                if (existingVariants.stream().noneMatch(v -> v.getVariantName().equals(variantDTO.getVariantName()))) {
                    productVariantService.createVariant(productId, variantDTO);
                }
            }

            for (ProductVariantDTO existingVariant : existingVariants) {
                if (updateRequest.getVariants().stream().noneMatch(v -> v.getVariantName().equals(existingVariant.getVariantName()))) {
                    productVariantService.deleteVariant(productId, existingVariant.getId());
                }
            }
        }

        Product updatedProduct = productRepository.save(product);
        return dtoMapper.toProductDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId, Long sellerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        if (!product.getSeller().getId().equals(sellerId)) {
            throw new org.springframework.security.access.AccessDeniedException("User not authorized to delete this product");
        }

        productRepository.delete(product);
        log.info("Product ID: {} deleted by seller ID: {}", productId, sellerId);
    }
}