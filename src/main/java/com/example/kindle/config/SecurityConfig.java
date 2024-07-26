package com.example.kindle.config;

import com.example.kindle.config.JWTResponeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JWTResponeFilter jwtResponeFilter;

    public SecurityConfig(JWTResponeFilter jwtResponeFilter) {
        this.jwtResponeFilter = jwtResponeFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtResponeFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/addUser", "/api/auth/login").permitAll()
                .requestMatchers("/api/books/add").authenticated()
                .anyRequest().permitAll();

        return http.build();
    }
}
