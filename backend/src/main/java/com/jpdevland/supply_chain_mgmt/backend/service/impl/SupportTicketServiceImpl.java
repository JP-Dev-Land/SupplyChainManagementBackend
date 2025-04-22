package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.support.SupportTicketDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.support.TicketResponseDTO;
import com.jpdevland.supply_chain_mgmt.backend.enums.TicketStatus;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.model.SupportTicket;
import com.jpdevland.supply_chain_mgmt.backend.model.TicketResponse;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import com.jpdevland.supply_chain_mgmt.backend.mapper.SupportTicketMapper;
import com.jpdevland.supply_chain_mgmt.backend.repo.SupportTicketRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.UserRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final SupportTicketMapper dtoMapper;

    @Override
    @Transactional
    public SupportTicketDTO createTicket(SupportTicketDTO ticketDTO) {
        User customer = userRepository.findById(ticketDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", ticketDTO.getCustomerId()));

        SupportTicket ticket = SupportTicket.builder()
                .customer(customer)
                .subject(ticketDTO.getSubject())
                .message(ticketDTO.getMessage())
                .status(TicketStatus.OPEN)
                .build();

        SupportTicket savedTicket = ticketRepository.save(ticket);
        return dtoMapper.toDto(savedTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public SupportTicketDTO getTicketById(Long ticketId) {
        SupportTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("SupportTicket", "id", ticketId));
        return dtoMapper.toDto(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDTO> getTicketsByCustomer(Long customerId, Pageable pageable) {
        return ticketRepository.findAllByCustomerId(customerId, pageable)
                .map(dtoMapper::toDto);
    }

    @Override
    @Transactional
    public SupportTicketDTO addResponseToTicket(Long ticketId, TicketResponseDTO responseDTO) {
        SupportTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("SupportTicket", "id", ticketId));

        User responder = userRepository.findById(responseDTO.getResponderId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", responseDTO.getResponderId()));

        TicketResponse response = TicketResponse.builder()
                .responder(responder)
                .message(responseDTO.getMessage())
                .build();

        ticket.addResponse(response);
        SupportTicket updatedTicket = ticketRepository.save(ticket);
        return dtoMapper.toDto(updatedTicket);
    }

    @Override
    @Transactional
    public SupportTicketDTO updateTicketStatus(Long ticketId, String status) {
        SupportTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("SupportTicket", "id", ticketId));

        TicketStatus ticketStatus;
        try {
            ticketStatus = TicketStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ticket status: " + status);
        }

        ticket.setStatus(ticketStatus);
        SupportTicket updatedTicket = ticketRepository.save(ticket);
        return dtoMapper.toDto(updatedTicket);
    }
}