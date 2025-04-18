package com.jpdevland.supply_chain_mgmt.backend.dto;

import lombok.Data;

import javax.management.relation.Role;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private Set<String> roles;
}
