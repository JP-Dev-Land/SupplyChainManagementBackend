package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.user.UpdateProfileRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.user.UserProfileDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.user.UpdateAvailabilityRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import com.jpdevland.supply_chain_mgmt.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users") // Base path for user-related actions
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET /api/users/me - Get current logged-in user's profile
    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile(@AuthenticationPrincipal User currentUser) {
        if (currentUser == null) {
            // Handle case where principal is unexpectedly null (shouldn't happen if authenticated)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserProfileDTO profile = userService.getCurrentUserProfile(currentUser.getId());
        return ResponseEntity.ok(profile);
    }

    // PUT /api/users/me - Update current logged-in user's profile (e.g., name)
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileDTO> updateCurrentUserProfile(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody UpdateProfileRequestDTO request) {
        UserProfileDTO updatedProfile = userService.updateCurrentUserProfile(currentUser.getId(), request);
        return ResponseEntity.ok(updatedProfile);
    }

    // PUT /api/users/me/availability - Update own availability (Delivery Agent)
    @PutMapping("/me/availability")
    @PreAuthorize("hasRole('DELIVERY_AGENT')") // Only delivery agents can set their availability
    public ResponseEntity<Void> updateMyAvailability(
            @Valid @RequestBody UpdateAvailabilityRequestDTO request,
            @AuthenticationPrincipal User currentUser) {

        userService.updateAvailability(currentUser.getId(), request.getAvailable(), currentUser.getId());
        return ResponseEntity.ok().build();
    }

    // TODO: Add endpoint for changing password in future
    // POST /api/users/me/change-password
}