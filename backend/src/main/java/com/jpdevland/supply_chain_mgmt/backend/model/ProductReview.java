package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWarDeployment;

import java.time.OffsetDateTime;

@Entity
@Table(name = "product_reviews" ,schema = "public")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private long product_id;

    @Column(nullable = false)
    private long customer_id;

    @Column(nullable = false)
    private  int rating;

    @Column(nullable = false)
    private  String review;

    @Column(nullable = false,columnDefinition = "TIMESTAMPZ")
    private OffsetDateTime review_date;

}
