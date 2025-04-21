package com.jpdevland.supply_chain_mgmt.backend.service;
import com.jpdevland.supply_chain_mgmt.backend.dto.user.*;

import java.util.List;

public interface UserService {
    // Admin Operations
    List<UserSummaryDTO> getAllUsers();
    UserDetailDTO getUserById(Long id);
    UserDetailDTO createUser(CreateUserRequestDTO request);
    UserDetailDTO updateUser(Long id, UpdateUserRequestDTO request);
    void deleteUser(Long id);

    // Self-Service Operations
    UserProfileDTO getCurrentUserProfile(Long userId);
    UserProfileDTO updateCurrentUserProfile(Long userId, UpdateProfileRequestDTO request);

    // For Delivery Agents
    void updateAvailability(Long userId, boolean available, Long requestingUserId);

    // For getting available delivery agents
    List<UserProfileDTO> getAvailableDeliveryAgents();
}
