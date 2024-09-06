package com.ramos.helpdesk.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	@Value("${jwt.expiration}")
	private Long expiration;
	
	@Value("${jwt.secret}")
	private String secret;
	
	private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generates a secure key

	public String generateToken(String email) {
		Key keys = Keys.hmacShaKeyFor(secret.getBytes());
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(key, SignatureAlgorithm.HS512)
				//.signWith(key)
				.compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if(claims!=null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new  Date(System.currentTimeMillis());
			if(username!= null && expirationDate!=null && now.before(expirationDate)) {
				return true;
			}					
		}
		return false;
	}
	
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(key.getEncoded()).parseClaimsJws(token).getBody();
			//return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
			//return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if(claims!=null) {
			return claims.getSubject();
		}
		return null;
	}
}
