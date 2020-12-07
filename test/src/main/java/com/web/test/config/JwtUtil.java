package com.web.test.config;

import java.security.Key;

import org.springframework.stereotype.Component;

import com.web.test.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private Key key;
	private int userno = 0;
	
	public JwtUtil() {
		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	}
	
	public String createToken(User user) {
		String jws = Jwts.builder().claim("id", user.getId()).claim("userno", user.getUserno()).signWith(key).compact();
		return jws;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public int getUserno() {
		return userno;
	}

	public void setUserno(int userno) {
		this.userno = userno;
	}
}
