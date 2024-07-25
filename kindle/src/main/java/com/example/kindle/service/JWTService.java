package com.example.kindle.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kindle.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiration}")
    private int expiry;
private final static String USER_NAME="name";
    private Algorithm algorithm;
    @PostConstruct
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);
    }
    public String generateToken(User user){
        return JWT.create().withClaim("name",user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiry))
                .withIssuer(issuer).sign(Algorithm.HMAC256(algorithmKey));
    }

    public String getUserName(String token) {
        DecodedJWT decodedJWT =JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(algorithmKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
