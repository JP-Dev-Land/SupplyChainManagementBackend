package com.jpdevland.supply_chain_mgmt.backend.dto.ai;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AiImageGenerateRequest {
    @NotBlank
    private String referenceImageUrl; // URL of the product image to base generation on
    private String prompt; // Optional: Additional text prompt for generation style
}