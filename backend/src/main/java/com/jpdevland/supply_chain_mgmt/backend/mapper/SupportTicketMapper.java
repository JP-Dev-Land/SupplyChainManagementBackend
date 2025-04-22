package com.jpdevland.supply_chain_mgmt.backend.mapper;

import com.jpdevland.supply_chain_mgmt.backend.dto.support.SupportTicketDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.SupportTicket;
import org.springframework.stereotype.Component;

@Component
public class SupportTicketMapper {

    public SupportTicketDTO toDto(SupportTicket ticket) {
        return SupportTicketDTO.builder()
                .id(ticket.getId())
                .customerId(ticket.getCustomer() != null ? ticket.getCustomer().getId() : null)
                .customerName(ticket.getCustomer() != null ? ticket.getCustomer().getName() : null)
                .subject(ticket.getSubject())
                .message(ticket.getMessage())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
}