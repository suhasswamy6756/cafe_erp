package com.cafe.erp.modules.auth.service;

import com.cafe.erp.modules.auth.dto.BaristaResponseDTO;
import com.cafe.erp.modules.auth.dto.LoginRequest;
import com.cafe.erp.modules.auth.dto.LoginResponse;
import com.cafe.erp.modules.user_roles.entity.UserRolesMapping;
import com.cafe.erp.modules.auth.entity.BaristaToken;
import com.cafe.erp.modules.auth.entity.Baristas;
import com.cafe.erp.modules.auth.entity.UserPrincipal;
import com.cafe.erp.modules.auth.respository.BaristaRepository;
import com.cafe.erp.modules.auth.respository.BaristaTokenRepository;
import com.cafe.erp.modules.roles.dto.RoleResponseDTO;
import com.cafe.erp.modules.roles.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    // ------------------------------------------------------------------------
    // Fetch all baristas
    // ------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<BaristaResponseDTO> getAllBaristas() {
        List<Baristas> baristas = baristasRepository.findAllWithRoles();

        return baristas.stream().map(barista -> {
            // Filter valid roles and flatten
            Set<RoleResponseDTO> roles = barista.getRoleMappings().stream()
                    .filter(rm -> rm.getRevokedAt() == null && !rm.isDeleted())
                    .map(UserRolesMapping::getRole)
                    .filter(Roles::isActive)
                    .map(role -> new RoleResponseDTO(
                            role.getRoleId(),
                            role.getRoleName(),
                            role.getRoleDescription(),
                            role.isActive()
                    ))
                    .collect(Collectors.toSet());

            // Return unified DTO
            return new BaristaResponseDTO(
                    barista.getUserId(),
                    barista.getUsername(),
                    barista.getFullName(),
                    barista.getLastLogin(),
                    barista.getPhoneNumber(),
                    barista.getCreatedBy(),
                    barista.getUpdatedBy(),
                    barista.getCreatedAt(),
                    barista.getUpdatedAt(),

                    roles
            );
        }).collect(Collectors.toList());
    }


    public Baristas registerBarista(Baristas barista) {
        barista.setPasswordHash(passwordEncoder.encode(barista.getPasswordHash()));
        return baristasRepository.save(barista);
    }

    // ------------------------------------------------------------------------
    // Login (returns tokens + clean DTO)
    // ------------------------------------------------------------------------
    @Transactional
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

        Baristas barista = baristasRepository.findByUsername(loginRequest.getUsername());


        // Revoke existing tokens
        List<BaristaToken> oldTokens = tokenRepository.findAllByBaristaAndRevokedFalse(barista);
        oldTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(oldTokens);

        // Generate new tokens
        String accessToken = jwtService.generateAccessToken(barista.getUsername());
        String refreshToken = jwtService.generateRefreshToken(barista.getUsername());

        tokenRepository.save(createTokenEntity(accessToken, barista, "ACCESS"));
        tokenRepository.save(createTokenEntity(refreshToken, barista, "REFRESH"));

        // Update last login
        barista.setLastLogin(OffsetDateTime.now());
        baristasRepository.save(barista);

        // ✅ Convert to DTO (no proxies)
        BaristaResponseDTO baristaDTO = mapToDTO(barista);

        return new LoginResponse(accessToken, refreshToken, baristaDTO);
    }

    // ------------------------------------------------------------------------
    // Refresh Access Token
    // ------------------------------------------------------------------------
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

        BaristaResponseDTO baristaDTO = mapToDTO(barista);

        return new LoginResponse(newAccessToken, refreshToken, baristaDTO);
    }

    // ------------------------------------------------------------------------
    // Logout (revoke current token)
    // ------------------------------------------------------------------------
    public void logoutBarista(String token) {
        BaristaToken baristaToken = tokenRepository.findByToken(token);
        if (baristaToken != null) {
            baristaToken.setExpired(true);
            baristaToken.setRevoked(true);
            baristaToken.setRevoked_at(ZonedDateTime.now().toOffsetDateTime());
            tokenRepository.save(baristaToken);
        }
    }

    // ------------------------------------------------------------------------
    // Helper: create token entity
    // ------------------------------------------------------------------------
    private BaristaToken createTokenEntity(String token, Baristas barista, String type) {
        BaristaToken baristaToken = new BaristaToken();
        baristaToken.setToken(token);
        baristaToken.setBarista(barista);
        baristaToken.setRevoked(false);
        baristaToken.setExpired(false);
        baristaToken.setToken_type(type);
        return baristaToken;
    }

    // ------------------------------------------------------------------------
    // Helper: map Barista -> DTO (flatten roles)
    // ------------------------------------------------------------------------
    private BaristaResponseDTO mapToDTO(Baristas barista) {
        Set<RoleResponseDTO> roles = barista.getRoleMappings().stream()
                .filter(rm -> rm.getRevokedAt() == null && !rm.isDeleted())  // ✅ filter revoked
                .map(UserRolesMapping::getRole)
                .filter(Roles::isActive)
                .map(role -> new RoleResponseDTO(
                        role.getRoleId(),
                        role.getRoleName(),
                        role.getRoleDescription(),
                        role.isActive()
                ))
                .collect(Collectors.toSet());

        return new BaristaResponseDTO(
                barista.getUserId(),
                barista.getUsername(),
                barista.getFullName(),
                barista.getLastLogin(),
                barista.getPhoneNumber(),
                barista.getCreatedBy(),
                barista.getUpdatedBy(),
                barista.getCreatedAt(),
                barista.getUpdatedAt(),
                roles

        );
    }

}
