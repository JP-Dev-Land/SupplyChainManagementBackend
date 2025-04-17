package com.jpdevland.supply_chain_mgmt.backend.dto;

import lombok.Data;

import javax.management.relation.Role;

@Data
public class ResponseUser {
    private String username;
    private Role role;
}
