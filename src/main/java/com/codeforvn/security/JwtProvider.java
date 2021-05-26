package com.codeforvn.security;

import com.codeforvn.exception.TodolistException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import com.codeforvn.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.Date;
import java.time.Instant;
import static io.jsonwebtoken.Jwts.parserBuilder;
import static java.util.Date.from;
@Service
public class JwtProvider {

    private KeyStore keyStore;
    private final char[] pwdArray = "secret".toCharArray();

    public void createKeyStore() {
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, pwdArray);
            FileOutputStream fos = new FileOutputStream("newKeyStore.jks");
            keyStore.store(fos, pwdArray);
        }
        catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            throw new TodolistException("Exception occurred while creating keystore");
        }
    }
    @PostConstruct
    public void init() {
        try {
            createKeyStore();
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("newKeyStore.jks");
            keyStore.load(resourceAsStream, pwdArray);
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new TodolistException("Exception occurred while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private Key getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("newKeyStore","secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new TodolistException("Exception occurred while retrieving public key from keystore");
        }
    }


}
