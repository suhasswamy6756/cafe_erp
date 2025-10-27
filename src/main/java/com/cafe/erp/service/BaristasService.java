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

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Invalid credentials");
        }

        // ✅ Get the user
        Baristas barista = baristasRepository.findByUsername(loginRequest.getUsername());

        // ✅ Revoke all old tokens
        List<BaristaToken> oldTokens = tokenRepository.findAllByBaristaAndRevokedFalse(barista);
        for (BaristaToken t : oldTokens) {
            t.setRevoked(true);
            t.setExpired(true);
        }
        tokenRepository.saveAll(oldTokens);

        // ✅ Generate new JWT
        String jwt = jwtService.generateToken(barista.getUsername());

        // ✅ Save new token in DB
        BaristaToken newToken = new BaristaToken();
        newToken.setToken(jwt);
        newToken.setBarista(barista);
        newToken.setRevoked(false);
        newToken.setExpired(false);
        tokenRepository.save(newToken);

        // ✅ Update last login
        barista.setLast_login(OffsetDateTime.now());
        baristasRepository.save(barista);

        return new LoginResponse(jwt, barista);
    }


    public void logoutBarista(String token) {
        BaristaToken baristaToken = tokenRepository.findByToken(token);
        baristaToken.setExpired(true);
        baristaToken.setRevoked(true);
        baristaToken.setRevoked_at(ZonedDateTime.now().toOffsetDateTime());
        tokenRepository.save(baristaToken);
    }
}
