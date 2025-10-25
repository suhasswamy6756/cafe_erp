package com.cafe.erp.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
    private Baristas barista;

    public UserPrincipal(Baristas barista) {
        this.barista = barista;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {

        return barista.getPassword_hash();
    }

    @Override
    public String getUsername() {
        return barista.getUsername();
    }

}
