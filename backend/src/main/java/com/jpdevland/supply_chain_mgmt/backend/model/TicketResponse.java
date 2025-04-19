package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "ticket_responses",schema = "public")
public class TicketResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long ticket_id;

    @Column(nullable = false)
    private long responder_id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false,columnDefinition = "TIMESTAMPZ")
    private OffsetDateTime created_at;

}
