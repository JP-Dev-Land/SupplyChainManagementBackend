package com.jpdevland.supply_chain_mgmt.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class AuthResponse {
    private String token;
}
