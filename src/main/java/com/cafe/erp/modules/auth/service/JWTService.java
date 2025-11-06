package com.cafe.erp.modules.auth.service;

import com.cafe.erp.common.config.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTService {


    private final JwtUtil jwtUtil;


    public String generateAccessToken(String username) {
        return buildToken(username, 1000L * 60 * 60 * 24);
    }

    public String generateRefreshToken(String username) {
        return buildToken(username, 1000L * 60 * 60 * 24 * 7); // 7 days
    }

    private String buildToken(String username, long expirationTimeMillis) {
        return Jwts.builder()
                .claims()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .and()
                .signWith(jwtUtil.getKey())
                .compact();
    }


    public boolean validToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(jwtUtil.getKey()).build().
                parseSignedClaims(token).getPayload();

    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
