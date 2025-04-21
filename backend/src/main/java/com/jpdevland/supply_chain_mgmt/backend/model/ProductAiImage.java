package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "product_ai_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAiImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image_url", nullable = false, length = 2048)
    private String imageUrl;

    @Column(name = "generated_at", nullable = false)
    private Instant generatedAt = Instant.now();
}