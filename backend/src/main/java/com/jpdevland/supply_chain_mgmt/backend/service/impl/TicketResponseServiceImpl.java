package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.support.TicketResponseCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.support.TicketResponseDTO;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.mapper.TicketResponseMapper;
import com.jpdevland.supply_chain_mgmt.backend.model.SupportTicket;
import com.jpdevland.supply_chain_mgmt.backend.model.TicketResponse;
import com.jpdevland.supply_chain_mgmt.backend.repo.SupportTicketRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.TicketResponseRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.TicketResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketResponseServiceImpl implements TicketResponseService {

    private final TicketResponseRepository ticketResponseRepository;
    private final SupportTicketRepository supportTicketRepository;
    private final TicketResponseMapper ticketResponseMapper;

    @Override
    @Transactional
    public TicketResponseDTO createTicketResponse(TicketResponseCreateRequest request) {
        SupportTicket ticket = supportTicketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new ResourceNotFoundException("SupportTicket", "id", request.getTicketId()));

        TicketResponse response = TicketResponse.builder()
                .ticket(ticket)
                .message(request.getMessage())
                .build();

        TicketResponse savedResponse = ticketResponseRepository.save(response);
        return ticketResponseMapper.toDto(savedResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getResponsesByTicketId(Long ticketId) {
        return ticketResponseRepository.findAll().stream()
                .filter(response -> response.getTicket().getId().equals(ticketId))
                .map(ticketResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTicketResponse(Long responseId) {
        TicketResponse response = ticketResponseRepository.findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("TicketResponse", "id", responseId));
        ticketResponseRepository.delete(response);
    }
}