package com.example.kindle.config;

import com.example.kindle.model.User;
import com.example.kindle.repository.UserRepository;
import com.example.kindle.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTResponeFilter extends OncePerRequestFilter {
    private static final Logger logger = LogManager.getLogger(JWTResponeFilter.class);

    public JWTResponeFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    private JWTService jwtService;
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Inside doFilterInternal method to fetch JWT token");
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                if (jwtService.validateToken(token)) {
                    String username = jwtService.getUserName(token);

                    Optional<User> opUser = userRepository.findByUsername(username);
                    if (opUser.isPresent()) {
                        User user = opUser.get();
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                        authentication.setDetails(new WebAuthenticationDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        logger.info("User not found");
                    }
                }
            } catch (Exception e) {
                logger.error("Token is invalid: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
