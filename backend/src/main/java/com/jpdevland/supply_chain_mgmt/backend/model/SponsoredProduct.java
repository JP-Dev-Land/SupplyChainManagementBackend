package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "sponsored_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SponsoredProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many sponsored products refer to one product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Many sponsored products refer to one sponsor (User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sponsor_id", nullable = false)
    private User sponsor;

    @Column(name = "start_date", nullable = false)
    private OffsetDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private OffsetDateTime endDate;

    @Column(name = "ad_text")
    private String adText;

    @Column(name = "budget", precision = 12, scale = 2)
    private BigDecimal budget;
}
