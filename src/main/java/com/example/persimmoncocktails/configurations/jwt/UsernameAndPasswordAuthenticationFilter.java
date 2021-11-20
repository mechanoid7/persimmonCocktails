package com.example.persimmoncocktails.configurations.jwt;

import com.example.persimmoncocktails.dtos.auth.RequestSigninDataDto;
import com.example.persimmoncocktails.models.Person;
import com.example.persimmoncocktails.services.AuthorizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@PropertySource("classpath:var/general.properties")
public class UsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final AuthorizationService authorizationService;

    public UsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, AuthorizationService authorizationService) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.authorizationService = authorizationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            RequestSigninDataDto requestSigninDataDto =
                    new ObjectMapper().readValue(request.getInputStream(), RequestSigninDataDto.class);

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    requestSigninDataDto.getEmail(), requestSigninDataDto.getPassword()
            );
            auth = authenticationManager.authenticate(auth);
            return auth;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        Long userId = ((Person)(authorizationService.loadUserByUsername(authResult.getName()))).getPersonId();
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userId);
        claims.put("authorities", authResult.getAuthorities());
//        AbstractAuthenticationToken abstractAuthenticationToken = (AbstractAuthenticationToken) authResult;
//        abstractAuthenticationToken.setDetails(userId);
//        authResult = abstractAuthenticationToken;
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(jwtConfig.secretKey())
                .compact();

        response.addHeader("Authorization", jwtConfig.getTokenPrefix() + token);
    }
}
