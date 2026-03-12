package co.edu.unbosque.gateway.util;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret:mySecureSecretKeyForTiendaGenericaWithMinimumLengthRequired}")
	private String jwtSecret;

	public String extractUsuario(String token) {
		return extractClaims(token).getSubject();
	}

	public String extractRol(String token) {
		return extractClaims(token).get("rol", String.class);
	}

	public Boolean validateToken(String token) {
		try {
			extractClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Claims extractClaims(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
