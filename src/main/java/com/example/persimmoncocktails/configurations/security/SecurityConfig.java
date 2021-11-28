package com.example.persimmoncocktails.configurations.security;

import com.example.persimmoncocktails.configurations.CorsFilter;
import com.example.persimmoncocktails.configurations.jwt.JwtConfig;
import com.example.persimmoncocktails.configurations.jwt.JwtTokenVerifier;
import com.example.persimmoncocktails.configurations.jwt.UsernameAndPasswordAuthenticationFilter;
import com.example.persimmoncocktails.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AuthorizationService authorizationService;
    private final JwtConfig jwtConfig;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, AuthorizationService authorizationService, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.authorizationService = authorizationService;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new CorsFilter(), UsernameAndPasswordAuthenticationFilter.class)
                .addFilter(new UsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, authorizationService))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig), UsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login", "/registration").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(authorizationService);
        return daoAuthenticationProvider;
    }
}
