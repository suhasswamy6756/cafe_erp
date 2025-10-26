package com.cafe.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cafe.erp.entity.Baristas;
import com.cafe.erp.entity.UserPrincipal;
import com.cafe.erp.repository.BaristaRepository;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private BaristaRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Baristas barista = userRepository.findByUsername(username);
        if (barista == null) {
            // return new MyUserDetails(barista);
            System.out.println("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new UserPrincipal(barista);
    }

}
