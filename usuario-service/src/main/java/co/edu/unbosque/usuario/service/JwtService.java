package co.edu.unbosque.usuario.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret:mySecureSecretKeyForTiendaGenericaWithMinimumLengthRequired}")
	private String jwtSecret;

	@Value("${jwt.expiration:86400000}")
	private long jwtExpiration;

	public String generateToken(String usuario, String rol) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		return Jwts.builder()
			.subject(usuario)
			.claim("rol", rol)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + jwtExpiration))
			.signWith(key)
			.compact();
	}

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
