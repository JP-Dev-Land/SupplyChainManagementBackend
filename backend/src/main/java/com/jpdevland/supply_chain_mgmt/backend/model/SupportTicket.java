package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "support_tickets",schema = "public")
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long customer_id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false,length = 50)
    private String status;

    @Column(nullable = false,columnDefinition = "TIMESTAMPZ")
    private OffsetDateTime created_at;

    @Column(nullable = false,columnDefinition = "TIMESTAMPZ")
    private OffsetDateTime updated_at;
}
