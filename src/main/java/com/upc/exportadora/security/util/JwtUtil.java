package com.upc.exportadora.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

// Genera y valida los tokens JWT
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 60 min
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		Claims claims = getClaims(token);
		return claims.getSubject().equals(userDetails.getUsername())
				&& claims.getExpiration().after(new Date());
	}

	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
}
