package com.jpdevland.supply_chain_mgmt.backend.dto.user;

import com.jpdevland.supply_chain_mgmt.backend.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Set;

@Data
public class UpdateUserRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String username;

    @NotEmpty
    private Set<Role> roles;

    @NotNull
    private Boolean enabled;

}