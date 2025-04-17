package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.AdminAnalyticsDTO;

public interface AnalyticsService {
    AdminAnalyticsDTO getAdminDashboardAnalytics();
}