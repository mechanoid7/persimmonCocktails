package com.example.persimmoncocktails.configurations.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.crypto.SecretKey;

@Data
//@AllArgsConstructor
@PropertySource("classpath:var/security.properties")
@Configuration
public class JwtConfig {
    @Value("${jwt_secret}")
    private String secretKey;
    @Value("${jwt_header_prefix}")
    private String tokenPrefix;

    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
