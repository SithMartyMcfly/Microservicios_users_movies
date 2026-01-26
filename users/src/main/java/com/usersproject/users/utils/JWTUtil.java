package com.usersproject.users.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@ConfigurationProperties(prefix = "custom.security.jwt")
public class JWTUtil {

    private String key;
    private String issuer;
    private long ttlMillis;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public String create(String id, String subject) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256);

        if (ttlMillis > 0) {
            builder.setExpiration(new Date(nowMillis + ttlMillis));
        }

        return builder.compact();
    }

    public String getSubject(String token) {
        return parse(token).getBody().getSubject();
    }

    public String getId(String token) {
        return parse(token).getBody().getId();
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    // Getters y setters para ConfigurationProperties
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public void setIssuer(String issuer) { this.issuer = issuer; }
    public void setTtlMillis(long ttlMillis) { this.ttlMillis = ttlMillis; }
}

