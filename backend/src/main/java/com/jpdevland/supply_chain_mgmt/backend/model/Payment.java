package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name="payments",schema = "public")
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(nullable = false)
    private BigDecimal order_id;

    @Column(nullable = false, length = 50)
    private String payment_gateway;

    @Column(nullable = false,length = 50)
    private String payment_status;

    @Column(nullable = false,length = 255)
    private String transaction_id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false,columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime payment_date=OffsetDateTime.now();

}
