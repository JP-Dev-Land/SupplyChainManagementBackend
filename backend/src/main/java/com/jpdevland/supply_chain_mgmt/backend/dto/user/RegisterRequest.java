package com.jpdevland.supply_chain_mgmt.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data public class RegisterRequest {
    @NotBlank
    String name;
    @Email
    String username;
    @NotBlank
    String password;
}
