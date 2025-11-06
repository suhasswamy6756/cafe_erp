package com.cafe.erp.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cafe.erp.auth.entity.Baristas;
import com.cafe.erp.auth.entity.UserPrincipal;
import com.cafe.erp.auth.repository.BaristaRepository;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private BaristaRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Baristas barista = userRepository.findByUsernameWithRoles(username);
        if (barista == null) {
            // return new MyUserDetails(barista);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new UserPrincipal(barista);
    }

}
