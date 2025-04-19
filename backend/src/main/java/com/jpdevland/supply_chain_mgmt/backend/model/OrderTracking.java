package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name ="order_tracking",schema = "public")
public class OrderTracking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long order_id;

    @Column(nullable = false,length = 50)
    private String status;

    @Column(nullable = false,length = 255)
    private String location;

    @Column(nullable = false,columnDefinition = "TIMESTAMPZ")
    private OffsetDateTime updated_at;

}
