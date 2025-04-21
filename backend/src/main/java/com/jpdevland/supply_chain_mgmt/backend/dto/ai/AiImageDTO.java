package com.jpdevland.supply_chain_mgmt.backend.dto.ai;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class AiImageDTO { // Response DTO
    private Long id;
    private Long productId;
    private String imageUrl;
    private OffsetDateTime generatedAt;
}