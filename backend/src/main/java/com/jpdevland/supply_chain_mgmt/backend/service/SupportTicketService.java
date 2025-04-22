package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.support.SupportTicketDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.support.TicketResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupportTicketService {
    SupportTicketDTO createTicket(SupportTicketDTO ticketDTO);
    SupportTicketDTO getTicketById(Long ticketId);
    Page<SupportTicketDTO> getTicketsByCustomer(Long customerId, Pageable pageable);
    SupportTicketDTO addResponseToTicket(Long ticketId, TicketResponseDTO responseDTO);
    SupportTicketDTO updateTicketStatus(Long ticketId, String status);
}