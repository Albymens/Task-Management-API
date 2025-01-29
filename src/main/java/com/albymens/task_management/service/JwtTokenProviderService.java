package com.albymens.task_management.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProviderService {
    @Value("${security.jwt.secret.key}")
    private String secretKey;
    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    public String generateToken(String username){
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseSignedClaims(token).getPayload();
            Date expiryDate = claims.getExpiration();
            return expiryDate.after(new Date());
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseSignedClaims(token)
                .getBody().getSubject();
    }

}
