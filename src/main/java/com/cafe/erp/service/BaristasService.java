package com.cafe.erp.service;

import com.cafe.erp.entity.Baristas;
import com.cafe.erp.entity.LoginRequest;
import com.cafe.erp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BaristasService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ Register a new barista
    public Baristas registerBarista(Baristas barista) {
        barista.setPassword_hash(passwordEncoder.encode(barista.getPassword_hash()));
        return userRepository.save(barista);
    }

    // ✅ Login logic
    public String loginBarista(LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginRequest.getUsername());
        } else {
            return "Login failed: Invalid credentials.";
        }
    }
}
