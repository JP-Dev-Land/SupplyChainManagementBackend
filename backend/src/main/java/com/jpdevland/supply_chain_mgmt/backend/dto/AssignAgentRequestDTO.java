package com.jpdevland.supply_chain_mgmt.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignAgentRequestDTO {
    @NotNull(message = "Delivery agent ID is required")
    private Long deliveryAgentId;
}