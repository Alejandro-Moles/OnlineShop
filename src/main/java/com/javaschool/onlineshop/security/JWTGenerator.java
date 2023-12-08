package com.javaschool.onlineshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JWTGenerator {

    // Generating a secret key for JWT signing using the HS512 algorithm
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // This method generates a JWT token based on the user's authentication information
    public String generateToken(Authentication authentication) {
        String userEmail = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        // Building the JWT token with user details, issuance date, expiration date, and signing it with the secret key
        String token = Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // Printing the generated token (for debugging purposes)
        System.out.println("New token:");
        System.out.println(token);

        return token;
    }

    // This method extracts the username from a given JWT token
    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // This method validates a JWT token, checking if it's not expired and has a correct signature
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            // If validation fails, throw an AuthenticationCredentialsNotFoundException with an appropriate message
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect", ex.fillInStackTrace());
        }
    }
}
