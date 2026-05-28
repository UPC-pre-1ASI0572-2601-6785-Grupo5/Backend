package com.fueltrack.platform.iam.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utility component for creating and validating JWT tokens.
 */
@Component
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMs;

    /**
     * Creates a new JWT service.
     *
     * @param secret the shared JWT secret
     * @param expirationMs the token validity window in milliseconds
     */
    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Generates a JWT for the given subject.
     *
     * @param subject the token subject
     * @return the compact JWT string
     */
    public String generateToken(String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extracts the token subject.
     *
     * @param token the JWT token
     * @return the token subject
     */
    public String extractSubject(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Validates the token against the expected subject.
     *
     * @param token the JWT token
     * @param expectedSubject the expected subject value
     * @return true when the token is valid
     */
    public boolean isTokenValid(String token, String expectedSubject) {
        Claims claims = parseClaims(token);
        return expectedSubject.equals(claims.getSubject()) && claims.getExpiration().after(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}