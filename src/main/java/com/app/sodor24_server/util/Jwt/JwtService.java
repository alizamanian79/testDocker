package com.app.sodor24_server.util.Jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtService {

    private final String secretKey = "0qH8YvZ6R4p1kM2sW9tX3eF7aJ6bC8dQ0rV1nL5yP2zA9mK4";
    private final Key key;

    private final long expiration = 1000 * 60 * 20;

    public JwtService() {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }


    public JwtResponseDto generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        // تبدیل به لیست رشته‌ای
        List<String> authoritiesList = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setSubject(username)
                .claim("authorities", authoritiesList) // آرایه JSON در claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();

        return JwtResponseDto.builder()
                .message("احراز هویت موفقیت امیز بود")
                .access_token(token)
                .timestamp(LocalDateTime.now())
                .build();

    }
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }

    public Collection<SimpleGrantedAuthority> extractAuthorities(String token) {
        List<String> authorities = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("authorities", List.class);

        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
