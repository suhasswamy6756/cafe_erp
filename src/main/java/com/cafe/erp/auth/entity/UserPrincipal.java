package com.cafe.erp.auth.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserPrincipal(Baristas barista) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<Roles> roles = barista.getRoles();
        // Normalize role to uppercase and add ROLE_ prefix
        if (roles == null || roles.isEmpty()) {
            return Set.of(); // no roles assigned
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
//
//    @Override
//    public boolean isEnabled() {
//        return barista.is_active();
//    }
}
