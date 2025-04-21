package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductAvailabilityRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductAvailabilityResponseDTO;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException; // Import a suitable exception
import com.jpdevland.supply_chain_mgmt.backend.model.Product; // Import Product
import com.jpdevland.supply_chain_mgmt.backend.model.ProductAvailability;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductAvailabilityRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRepository; // Import ProductRepository
import com.jpdevland.supply_chain_mgmt.backend.service.ProductAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Good practice for update operations

import java.time.OffsetDateTime; // Keep this import
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductAvailabilityServiceImpl implements ProductAvailabilityService {

    private final ProductAvailabilityRepository availabilityRepository; // Renamed for clarity
    private final ProductRepository productRepository; // Inject ProductRepository

    @Override
    @Transactional // Ensure atomicity
    public ProductAvailabilityResponseDTO createOrUpdate(ProductAvailabilityRequestDTO requestDTO) {
        // 1. Fetch the Product entity first
        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID" ,requestDTO.getProductId()));

        // 2. Find existing availability using the Product object and region
        ProductAvailability availability = availabilityRepository.findByProductAndRegion(product, requestDTO.getRegion())
                .orElseGet(() -> ProductAvailability.builder() // Use orElseGet for lazy instantiation
                        .product(product) // Set the Product object
                        .region(requestDTO.getRegion())
                        .build());

        // 3. Update availability status (and let @UpdateTimestamp handle updatedAt)
        availability.setAvailable(requestDTO.getAvailable());
        // No need to manually setUpdatedAt if using @UpdateTimestamp in the entity

        // 4. Save the entity
        ProductAvailability saved = availabilityRepository.save(availability);

        // 5. Map to response DTO
        return mapToResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true) // Good practice for read operations
    public ProductAvailabilityResponseDTO getById(Long id) {
        ProductAvailability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductAvailability", "ID", id));
        return mapToResponseDTO(availability);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductAvailabilityResponseDTO> getAll() {
        return availabilityRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Optional: Check if exists before deleting to provide a better error or simply let it fail if not found
        if (!availabilityRepository.existsById(id)) {
            throw new ResourceNotFoundException("ProductAvailability","ID", id);
        }
        availabilityRepository.deleteById(id);
    }

    // --- Helper Method ---
    private ProductAvailabilityResponseDTO mapToResponseDTO(ProductAvailability entity) {
        if (entity == null) {
            return null;
        }
        return ProductAvailabilityResponseDTO.builder()
                .id(entity.getId())
                // Extract productId from the nested Product object
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .region(entity.getRegion())
                .available(entity.getAvailable())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}