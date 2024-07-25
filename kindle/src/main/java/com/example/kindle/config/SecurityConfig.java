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

        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtResponeFilter, UsernamePasswordAuthenticationFilter.class);

        // Define URL access rules
        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/addUser", "/api/auth/login").permitAll()  // Allow access to registration and login
                .requestMatchers("/api/books/add").authenticated()  // Require authentication for adding books
                .anyRequest().permitAll();  // Allow access to all other endpoints

        return http.build();
    }
}
