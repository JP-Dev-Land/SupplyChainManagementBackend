package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.user.AdminAnalyticsDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.Role;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import com.jpdevland.supply_chain_mgmt.backend.repo.UserRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true) // Read-only transaction for analytics
    public AdminAnalyticsDTO getAdminDashboardAnalytics() {

        // Fetch all users once for efficiency
        List<User> allUsers = userRepository.findAll();

        // Calculate User Counts
        long totalUsers = allUsers.size();
        long totalSellers = allUsers.stream()
                .filter(user -> user.getRoles().contains(Role.ROLE_SELLER))
                .count();
        long totalDeliveryAgents = allUsers.stream()
                .filter(user -> user.getRoles().contains(Role.ROLE_DELIVERY_AGENT))
                .count();

        // Calculate Users by Role (counting each role occurrence)
        Map<String, Long> usersByRole = allUsers.stream()
                .flatMap(user -> user.getRoles().stream()) // Flatten the stream of roles
                .collect(Collectors.groupingBy(Role::name, Collectors.counting()));

        // Build the DTO
        return AdminAnalyticsDTO.builder()
                .totalUsers(totalUsers)
                .totalSellers(totalSellers)
                .totalDeliveryAgents(totalDeliveryAgents)
                .usersByRole(usersByRole)
                .build();
    }
}