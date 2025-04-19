package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.user.AdminAnalyticsDTO;

public interface AnalyticsService {
    AdminAnalyticsDTO getAdminDashboardAnalytics();
}