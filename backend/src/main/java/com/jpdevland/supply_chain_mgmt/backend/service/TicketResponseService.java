package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.support.TicketResponseCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.support.TicketResponseDTO;

import java.util.List;

public interface TicketResponseService {

    TicketResponseDTO createTicketResponse(TicketResponseCreateRequest request);

    List<TicketResponseDTO> getResponsesByTicketId(Long ticketId);

    void deleteTicketResponse(Long responseId);
}