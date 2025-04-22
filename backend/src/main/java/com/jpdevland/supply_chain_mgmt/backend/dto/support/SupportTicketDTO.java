package com.jpdevland.supply_chain_mgmt.backend.dto.support;

import com.jpdevland.supply_chain_mgmt.backend.enums.TicketStatus;

import lombok.Builder;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class SupportTicketDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private String subject;
    private String message;
    private TicketStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<TicketResponseDTO> responses;
}