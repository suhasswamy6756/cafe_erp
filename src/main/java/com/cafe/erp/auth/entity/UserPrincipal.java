package com.cafe.erp.auth.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserPrincipal(Baristas barista) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Normalize role to uppercase and add ROLE_ prefix
        return List.of(new SimpleGrantedAuthority("ROLE_" + barista.getRole().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return barista.getPassword_hash();
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
