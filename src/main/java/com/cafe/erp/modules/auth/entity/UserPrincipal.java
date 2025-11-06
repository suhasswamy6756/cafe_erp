package com.cafe.erp.modules.auth.entity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.cafe.erp.modules.roles.entity.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserPrincipal(Baristas barista) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Roles> roles = barista.getActiveRoles();  // now populated via helper
        System.out.println("Roles: " + roles);

        if (roles == null || roles.isEmpty()) {
            return Set.of();
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return barista.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return barista.getUsername();
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
        return barista.isActive();
    }
}
