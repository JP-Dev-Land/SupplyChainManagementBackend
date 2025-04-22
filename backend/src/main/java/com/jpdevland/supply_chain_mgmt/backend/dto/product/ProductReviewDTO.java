package com.jpdevland.supply_chain_mgmt.backend.dto.product;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class ProductReviewDTO {
    private Long id;
    private Long productId;
    private Long customerId;
    private String customerName;
    private int rating;
    private String review;
    private OffsetDateTime reviewDate;
}