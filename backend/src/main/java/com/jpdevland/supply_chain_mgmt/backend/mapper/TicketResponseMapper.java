package com.jpdevland.supply_chain_mgmt.backend.mapper;

import com.jpdevland.supply_chain_mgmt.backend.dto.support.TicketResponseDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.TicketResponse;
import org.springframework.stereotype.Component;

@Component
public class TicketResponseMapper {

    public TicketResponseDTO toDto(TicketResponse response) {
        return TicketResponseDTO.builder()
                .id(response.getId())
                .ticketId(response.getTicket().getId())
                .responderId(response.getResponder() != null ? response.getResponder().getId() : null)
                .responderName(response.getResponder() != null ? response.getResponder().getName() : null)
                .message(response.getMessage())
                .responseDate(response.getResponseDate())
                .build();
    }
}