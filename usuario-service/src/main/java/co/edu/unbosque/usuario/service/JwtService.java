/**
 * This package contains service classes responsible for
 * handling user authentication and authorization,
 * including generation and validation of JWT (JSON Web Tokens)
 * for secure access to application endpoints.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.usuario.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Service class responsible for managing JSON Web Tokens (JWT).
 * It provides methods for generating tokens, extracting information
 * from tokens, and validating token integrity and expiration.
 * 
 * Author: Wilmer Ramos
 */
@Service
public class JwtService {

    /** Secret key used to sign the JWT tokens (can be configured in application.properties) */
    @Value("${jwt.secret:mySecureSecretKeyForTiendaGenericaWithMinimumLengthRequired}")
    private String jwtSecret;

    /** Expiration time for the JWT token in milliseconds (default 1 day) */
    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;

    /**
     * Generates a JWT token for a given username and role.
     * 
     * @param usuario username of the user
     * @param rol role of the user
     * @return JWT token as a String
     */
    public String generateToken(String usuario, String rol) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.builder()
            .subject(usuario)               // sets the username as the subject
            .claim("rol", rol)              // adds the role as a custom claim
            .issuedAt(new Date())           // sets the issue date to now
            .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // sets expiration
            .signWith(key)                  // signs the token with HMAC-SHA key
            .compact();
    }

    /**
     * Extracts the username (subject) from the JWT token.
     * 
     * @param token JWT token
     * @return username stored in the token
     */
    public String extractUsuario(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Extracts the role of the user from the JWT token.
     * 
     * @param token JWT token
     * @return role stored in the token
     */
    public String extractRol(String token) {
        return extractClaims(token).get("rol", String.class);
    }

    /**
     * Validates the integrity and expiration of the JWT token.
     * 
     * @param token JWT token
     * @return true if the token is valid, false otherwise
     */
    public Boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts all claims (payload) from the JWT token.
     * 
     * @param token JWT token
     * @return Claims object containing all information in the token
     */
    private Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        // Parses and validates the token using the secret key
        return Jwts.parser()
            .verifyWith(key)             // verifies the signature
            .build()
            .parseSignedClaims(token)    // parses the signed claims
            .getPayload();               // returns the claims (subject, roles, etc.)
    }
}