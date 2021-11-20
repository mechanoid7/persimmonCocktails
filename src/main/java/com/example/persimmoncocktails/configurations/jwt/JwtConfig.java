package com.example.persimmoncocktails.configurations.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Data
//@AllArgsConstructor
@Configuration
public class JwtConfig {
    private String secretKey = "ketketketketketketketketketketketketketketketketketketketketketket";
    private String tokenPrefix = "Bearer ";

    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
