package com.yvolabs.projectmanagementsystemserver.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author Yvonne N
 * @apiNote This Application is not role based so no need of passing GrantedAuthorities to the jwt
 */
public class JwtProvider {
    private static final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {

        int expiryTimeInMs = 86400000; //24hrs

        Date expiryDate = new Date((new Date()).getTime() + expiryTimeInMs);
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(expiryDate) // after 24hrs
                .claim("email", auth.getName())
                .signWith(key)
                .compact();
    }

    public static String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return String.valueOf(claims.get("email"));
    }
}
