package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "return_policies")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReturnPolicy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Or OneToOne if one policy per product
    @JoinColumn(name = "product_id", nullable = false, unique = true) // Assuming unique per product
    private Product product;

    @Column(name = "return_window_days", nullable = false)
    private int returnWindowDays;

    @Column(name = "is_returnable", nullable = false)
    private boolean isReturnable = true;
}