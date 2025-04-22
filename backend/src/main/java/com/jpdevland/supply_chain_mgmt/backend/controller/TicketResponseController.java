package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.support.TicketResponseCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.support.TicketResponseDTO;
import com.jpdevland.supply_chain_mgmt.backend.service.TicketResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-responses")
@RequiredArgsConstructor
public class TicketResponseController {

    private final TicketResponseService ticketResponseService;

    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicketResponse(@Valid @RequestBody TicketResponseCreateRequest request) {
        TicketResponseDTO createdResponse = ticketResponseService.createTicketResponse(request);
        return new ResponseEntity<>(createdResponse, HttpStatus.CREATED);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<TicketResponseDTO>> getResponsesByTicketId(@PathVariable Long ticketId) {
        List<TicketResponseDTO> responses = ticketResponseService.getResponsesByTicketId(ticketId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{responseId}")
    public ResponseEntity<Void> deleteTicketResponse(@PathVariable Long responseId) {
        ticketResponseService.deleteTicketResponse(responseId);
        return ResponseEntity.noContent().build();
    }
}