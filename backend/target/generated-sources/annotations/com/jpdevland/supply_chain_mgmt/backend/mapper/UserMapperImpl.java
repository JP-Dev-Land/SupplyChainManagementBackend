package com.jpdevland.supply_chain_mgmt.backend.mapper;

import com.jpdevland.supply_chain_mgmt.backend.dto.user.UserDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.Role;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-19T21:21:04+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setRoles( roleSetToStringSet( user.getRoles() ) );
        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );
        userDTO.setUsername( user.getUsername() );

        afterToDto( user, userDTO );

        return userDTO;
    }

    protected Set<String> roleSetToStringSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<String> set1 = new LinkedHashSet<String>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( role.name() );
        }

        return set1;
    }
}
