package com.jpdevland.supply_chain_mgmt.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.authority.SimpleGrantedAuthority; 
import org.springframework.security.core.userdetails.UserDetails; 

import java.util.Collection; 
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors; 

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username; // email (used by UserDetails)

    @Column(nullable = false)
    private String password; // (used by UserDetails)

    // Assuming Role is an Enum like: public enum Role { ROLE_ADMIN, ROLE_SELLER, ... }
    @ElementCollection(fetch = FetchType.EAGER) // EAGER fetch ensures roles are loaded for getAuthorities()
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Builder.Default
    @Column(nullable = false)
    private boolean enabled = true; // (used by UserDetails)

    @Builder.Default
    @Column(nullable = false)
    private boolean available = false;

    // --- UserDetails Methods Implementation ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the Set<Role> (Enum) to Set<GrantedAuthority>
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name())) // Assuming Role enum constants match "ROLE_ADMIN", etc.
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        // UserDetails uses getUsername(), which maps to your email field
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}