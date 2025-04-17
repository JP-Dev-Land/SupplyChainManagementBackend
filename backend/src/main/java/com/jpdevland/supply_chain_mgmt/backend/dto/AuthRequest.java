package com.jpdevland.supply_chain_mgmt.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data public class AuthRequest  {
    @Email
    String username;
    @NotBlank
    String password;
}