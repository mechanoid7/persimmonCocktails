package com.example.persimmoncocktails.configurations.security;

import com.example.persimmoncocktails.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/registration").permitAll()
                .antMatchers("/person/**").hasRole(ApplicationUserRole.CLIENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService(){
//        Person admin = new Person(0L, "admin", "admin", passwordEncoder.encode("pass"), null, null, 1);
//
//        Person moderator = new Person(1L, "moder", "moder", passwordEncoder.encode("pass"), null, null, 2);
//
//        Person client = new Person(2L, "client", "client", passwordEncoder.encode("pass"), null, null, 3);

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("pass"))
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();

        UserDetails moderator = User.builder()
                .username("moder")
                .password(passwordEncoder.encode("pass"))
                .authorities(ApplicationUserRole.MODERATOR.getGrantedAuthorities())
                .build();

        UserDetails client = User.builder()
                .username("client")
//                .roles(ApplicationUserRole.CLIENT.name())
                .password(passwordEncoder.encode("pass"))
                .authorities(ApplicationUserRole.CLIENT.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(admin, moderator, client);
    }
}
