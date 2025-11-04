package com.cafe.erp.auth.service;

import com.cafe.erp.auth.DTO.LoginRequest;
import com.cafe.erp.auth.DTO.LoginResponse;
import com.cafe.erp.auth.entity.*;
import com.cafe.erp.auth.repository.BaristaRepository;
import com.cafe.erp.auth.repository.BaristaTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class BaristasService {

    @Autowired
    private BaristaRepository baristasRepository;

    @Autowired
    private BaristaTokenRepository tokenRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Baristas> getAllBaristas() {
        return baristasRepository.findAll();
    }

    // Register a new barista
    public Baristas registerBarista(Baristas barista) {
        barista.setPassword_hash(passwordEncoder.encode(barista.getPassword_hash()));
        return baristasRepository.save(barista);
    }

    //  Login logic (returns token + barista info)
    public LoginResponse loginBarista(LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Invalid credentials");
        }

        // Get the user
        Baristas barista = baristasRepository.findByUsername(loginRequest.getUsername());

        // Revoke all old tokens
        List<BaristaToken> oldTokens = tokenRepository.findAllByBaristaAndRevokedFalse(barista);
        for (BaristaToken t : oldTokens) {
            t.setRevoked(true);
            t.setExpired(true);
        }
        tokenRepository.saveAll(oldTokens);

        String accessToken = jwtService.generateAccessToken(barista.getUsername());
        String refreshToken = jwtService.generateRefreshToken(barista.getUsername());



        tokenRepository.save(createTokenEntity(accessToken, barista, "ACCESS"));
        tokenRepository.save(createTokenEntity(refreshToken, barista, "REFRESH"));

        // Update last login
        barista.setLast_login(OffsetDateTime.now());
        baristasRepository.save(barista);

        return new LoginResponse(accessToken, refreshToken, barista);
    }

    public LoginResponse refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        Baristas barista = baristasRepository.findByUsername(username);

        if (barista == null) {
            throw new RuntimeException("User not found");
        }

        UserPrincipal userPrincipal = new UserPrincipal(barista);

        if (!jwtService.validToken(refreshToken, userPrincipal)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        String newAccessToken = jwtService.generateAccessToken(username);

        tokenRepository.save(createTokenEntity(newAccessToken, barista, "ACCESS"));

        return new LoginResponse(newAccessToken, refreshToken, barista);
    }



    public void logoutBarista(String token) {
        BaristaToken baristaToken = tokenRepository.findByToken(token);
        baristaToken.setExpired(true);
        baristaToken.setRevoked(true);
        baristaToken.setRevoked_at(ZonedDateTime.now().toOffsetDateTime());
        tokenRepository.save(baristaToken);
    }
    private BaristaToken createTokenEntity(String token, Baristas barista, String type) {
        BaristaToken baristaToken = new BaristaToken();
        baristaToken.setToken(token);
        baristaToken.setBarista(barista);
        baristaToken.setRevoked(false);
        baristaToken.setExpired(false);
        baristaToken.setToken_type(type);
        return baristaToken;
    }
}
