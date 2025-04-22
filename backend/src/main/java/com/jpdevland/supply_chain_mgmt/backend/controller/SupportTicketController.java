package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.support.SupportTicketDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.support.TicketResponseDTO;
import com.jpdevland.supply_chain_mgmt.backend.service.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/support-tickets")
@RequiredArgsConstructor
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    @PostMapping
    public ResponseEntity<SupportTicketDTO> createTicket(@RequestBody SupportTicketDTO ticketDTO) {
        SupportTicketDTO createdTicket = supportTicketService.createTicket(ticketDTO);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<SupportTicketDTO> getTicketById(@PathVariable Long ticketId) {
        SupportTicketDTO ticket = supportTicketService.getTicketById(ticketId);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<SupportTicketDTO>> getTicketsByCustomer(@PathVariable Long customerId,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SupportTicketDTO> tickets = supportTicketService.getTicketsByCustomer(customerId, pageable);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/{ticketId}/responses")
    public ResponseEntity<SupportTicketDTO> addResponseToTicket(@PathVariable Long ticketId,
                                                                 @RequestBody TicketResponseDTO responseDTO) {
        SupportTicketDTO updatedTicket = supportTicketService.addResponseToTicket(ticketId, responseDTO);
        return ResponseEntity.ok(updatedTicket);
    }

    @PatchMapping("/{ticketId}/status")
    public ResponseEntity<SupportTicketDTO> updateTicketStatus(@PathVariable Long ticketId,
                                                                @RequestParam String status) {
        SupportTicketDTO updatedTicket = supportTicketService.updateTicketStatus(ticketId, status);
        return ResponseEntity.ok(updatedTicket);
    }
}