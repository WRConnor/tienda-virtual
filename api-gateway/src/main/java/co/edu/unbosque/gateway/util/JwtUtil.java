/**
 * Package containing utility classes for the API Gateway.
 * These classes provide helper methods for authentication,
 * authorization, and token management.
 * 
 * Author: Wilmer Ramos
 */
package co.edu.unbosque.gateway.util;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Utility class for handling JWT (JSON Web Token) operations.
 * Provides methods to extract user information, roles, and 
 * validate tokens using a secret key.
 * 
 * Author: Wilmer Ramos
 */
@Component
public class JwtUtil {

    /**
     * Secret key used to sign and validate JWT tokens.
     * Configurable via application properties.
     */
    @Value("${jwt.secret:mySecureSecretKeyForTiendaGenericaWithMinimumLengthRequired}")
    private String jwtSecret;

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token JWT token
     * @return Username contained in the token
     */
    public String extractUsuario(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Extracts the role from the JWT token.
     *
     * @param token JWT token
     * @return Role contained in the token
     */
    public String extractRol(String token) {
        return extractClaims(token).get("rol", String.class);
    }

    /**
     * Validates the JWT token by checking its signature and claims.
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
     * Extracts all claims from the JWT token.
     *
     * @param token JWT token
     * @return Claims object containing token data
     */
    private Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}


