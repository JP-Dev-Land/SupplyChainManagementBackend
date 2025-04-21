package com.jpdevland.supply_chain_mgmt.backend.mapper;

import com.jpdevland.supply_chain_mgmt.backend.dto.user.UserDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.Role;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "roles")
    UserDTO toDto(User user);

    @AfterMapping
    default void afterToDto(User user, @MappingTarget UserDTO dto) {
        if (user.getRoles() != null) {
            dto.setRoles(
                    user.getRoles()
                            .stream()
                            .map(Role::name)
                            .collect(Collectors.toSet())
            );
        }
    }
}
