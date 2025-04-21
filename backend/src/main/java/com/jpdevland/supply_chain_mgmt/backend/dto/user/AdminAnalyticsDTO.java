package com.jpdevland.supply_chain_mgmt.backend.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AdminAnalyticsDTO {
    private long totalUsers;
    private long totalSellers;
    private long totalDeliveryAgents;
    private Map<String, Long> usersByRole;    // e.g., {"ROLE_USER": 100, "ROLE_ADMIN": 5}
    // Add more complex analytics later if needed:
}
