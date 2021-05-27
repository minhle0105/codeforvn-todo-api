package com.codeforvn.security;

import com.codeforvn.exception.TodolistException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret;
    private int jwtExpiration = 900000;

    String generateKey() {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            key.append(UUID.randomUUID().toString());
        }
        key = new StringBuilder(key.toString().replaceAll("-", "a"));
        return key.toString();
    }

    JwtProvider() {
        jwtSecret = generateKey();
    }

    public String generateJwtToken(Authentication authentication) {

        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpiration)))
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new TodolistException("Cannot validate jwt token");
        }
    }

    public String getUserNameFromJwtToken(String token) {

        String userName = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userName;
    }


}
