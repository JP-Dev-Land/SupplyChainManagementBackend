package com.jpdevland.supply_chain_mgmt.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfileRequestDTO {
    @NotBlank
    private String name;
    // Only allow updating name through this DTO for now TODO: update later
}