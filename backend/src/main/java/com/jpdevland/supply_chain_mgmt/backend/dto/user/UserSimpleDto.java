package com.jpdevland.supply_chain_mgmt.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Simplified User DTO for embedding in other DTOs
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSimpleDto {
    private Long id;
    private String username;
    private String name;
}
