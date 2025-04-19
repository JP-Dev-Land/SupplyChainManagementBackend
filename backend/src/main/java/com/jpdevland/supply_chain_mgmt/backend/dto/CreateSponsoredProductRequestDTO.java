package com.jpdevland.supply_chain_mgmt.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class CreateSponsoredProductRequestDTO {
    private Long productId;
    private Long sponsorId;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private String adText;
    private BigDecimal budget;
}
