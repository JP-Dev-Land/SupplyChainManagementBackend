package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;
import lombok.*; // Keep individual annotations for clarity with JPA
import org.hibernate.annotations.UpdateTimestamp; // Use UpdateTimestamp for auto-update

import java.time.OffsetDateTime;

@Entity
@Table(name = "product_availability", uniqueConstraints = {
        // Ensure a product can only have one availability entry per region
        @UniqueConstraint(columnNames = {"product_id", "region"})
})
@Getter // Use specific Lombok annotations
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- THIS IS THE CORRECTED MAPPING ---
    @ManyToOne(fetch = FetchType.LAZY) // Defines the owning side of the relationship
    @JoinColumn(name = "product_id", nullable = false) // Specifies the foreign key column
    private Product product; // Field name MUST match the 'mappedBy' value in Product
    // --- Removed: private Long productId; ---

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    @Builder.Default // Set default value via builder
    private Boolean available = true;

    @UpdateTimestamp // Automatically update this field on modification
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt; // No need to initialize here if using @UpdateTimestamp
}