package com.warrior.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * @author Vaibhav
 * In that {@link JwtProvider} to create here in config package  hence one for generate JWT token and get from email to JWT Token
 * there two method is  created to need  here 
 * 1.----> to generate JWT token
 * 2.----> to get from email JWT token
 * 
 * 
 * why  created  granted autherity format again here already   gives there in granted autherity in CustomerUserServiceImpl here?
 * why i am converting  here string  format again  granted autherity from the String  ?
 * 
 * b/z  JWT token is not allow us to store  granted autherity format thats why we need to convert our authorities into String
 * we can set claim that means jwt token in string format.
 * 
 *
 */
@Service
public class JwtProvider {
	private SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generateToken(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
		String roles=populateAuthorities(authorities);
		String  jwt=Jwts.builder().setIssuedAt(new Date())
				.setExpiration((new Date(new Date().getTime()+86400000)))
				.claim("email", auth.getName())
				.claim("authorities", roles)
				.signWith(key)
				.compact();
				
		
		return jwt;
		
	}
	//create method get email from JWT token
	public String getEmailFromJwtToken(String jwt) {
		jwt=jwt.substring(7);
		Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email=String.valueOf(claims.get("email"));
		return email;
		
	}

	public String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> auths=new HashSet<>();
		for(GrantedAuthority authority:authorities) {
			auths.add(authority.getAuthority());
		}
		return String.join(",", auths);
		
	}

	

}
