package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
@Entity
@Table(name = "returns",schema = "public")
public class Return
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;

    @Column(nullable = false)
    private BigDecimal order_item_id;

    @Column(nullable = false)
    private String return_reason;

    @Column(nullable = false,length = 50)
    private String return_status;

    @Column(nullable = false,columnDefinition = "TIMESTAMPZ")
    private OffsetDateTime request_date;

    @Column(nullable = false,columnDefinition = "TIMESTAMPZ")
    private OffsetDateTime processed_date;

}
