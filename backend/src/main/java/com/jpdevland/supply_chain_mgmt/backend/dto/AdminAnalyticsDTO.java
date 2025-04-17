package com.jpdevland.supply_chain_mgmt.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class AdminAnalyticsDTO {
    private long totalUsers;
    private long totalCooks;
    private long totalDeliveryAgents;
    private Map<String, Long> usersByRole;    // e.g., {"ROLE_USER": 100, "ROLE_ADMIN": 5}
    // Add more complex analytics later if needed:
}
