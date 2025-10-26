package com.cafe.erp.service;

import com.cafe.erp.entity.BaristaToken;
import com.cafe.erp.entity.Baristas;
import com.cafe.erp.entity.LoginRequest;
import com.cafe.erp.entity.LoginResponse;
import com.cafe.erp.repository.BaristaRepository;
import com.cafe.erp.repository.BaristaTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class BaristasService {

    @Autowired
    private BaristaRepository baristasRepository;

    @Autowired
    private BaristaTokenRepository baristaTokenRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ Register a new barista
    public Baristas registerBarista(Baristas barista) {
        barista.setPassword_hash(passwordEncoder.encode(barista.getPassword_hash()));
        return baristasRepository.save(barista);
    }

    // ✅ Login logic (returns token + barista info)
    public LoginResponse loginBarista(LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            Baristas barista = baristasRepository.findByUsername(loginRequest.getUsername());


            // ✅ Update last login timestamp
            barista.setLast_login(ZonedDateTime.now().toOffsetDateTime());
            baristasRepository.save(barista);

            // ✅ Generate JWT
            String token = jwtService.generateToken(barista.getUsername());

            BaristaToken baristaToken = BaristaToken.builder()
                    .token(token)
                    .barista(barista)
                    .created_at(ZonedDateTime.now().toOffsetDateTime())
                    .updated_at(ZonedDateTime.now().toOffsetDateTime())
                    .created_by(barista.getUsername())
                    .build();

            baristaTokenRepository.save(baristaToken);

            // ✅ Return both token and barista
            return new LoginResponse(token, barista);
        } else {
            throw new RuntimeException("Login failed: Invalid credentials.");
        }
    }

    public void logoutBarista(String token) {
        BaristaToken baristaToken = baristaTokenRepository.findByToken(token);
        baristaToken.setExpired(true);
        baristaToken.setRevoked(true);
        baristaToken.setRevoked_at(ZonedDateTime.now().toOffsetDateTime());
        baristaTokenRepository.save(baristaToken);
    }
}
