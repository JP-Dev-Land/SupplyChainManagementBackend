package com.jpdevland.supply_chain_mgmt.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import com.jpdevland.supply_chain_mgmt.backend.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDTO {
    private Long id;
    private String name;
    private String username; // email
    private Set<Role> roles;
    private boolean enabled;
}