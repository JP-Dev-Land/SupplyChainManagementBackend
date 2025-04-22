package com.jpdevland.supply_chain_mgmt.backend.dto.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketResponseCreateRequest {
    @NotNull
    private Long ticketId;

    @NotBlank
    private String message;
}