package com.sanbro.AuthenticationService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtility {
    @Value("${secret.key}")
    private String secretKey;

    private Key key;

    @PostConstruct
    public void setValue(){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createJwtToken(Authentication authentication){
        // JwtToken is basically made up of 3 parts - Header, payload and signature
        // Header consists of type and algo
        // payload consists of actual info subject (which uniquely identifies a user) and the claims(any additional info)
        // signature consists of the combination of the header and payload signed with the some algorithm and some secret key
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("role",authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (7*24*60*60*1000)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token){
        log.info("printing in the utility method "+getClaims(token).getSubject());
        return getClaims(token).getSubject();
    }
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)                // Use the same key that was used to sign the token
                .build()
                .parseClaimsJws(token)             // Verifies signature
                .getBody();                        // Returns the actual claims (payload)
    }

    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }
    public boolean isTokenExpired(String token) {
        Date expirationDate = extractExpiration(token);
        return expirationDate.before(new Date()); // true if token has expired
    }
    public boolean validateToken(String token, UserDetails userDetails){
        String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
