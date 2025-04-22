package com.jpdevland.supply_chain_mgmt.backend.dto.support;

import lombok.Builder;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Builder
public class TicketResponseDTO {
    private Long id;
    private Long ticketId;
    private Long responderId;
    private String responderName;
    private String message;
    private OffsetDateTime responseDate;
}