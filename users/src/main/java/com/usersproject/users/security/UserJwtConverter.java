package com.usersproject.users.security;

import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.lang.NonNull;

public class UserJwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    @NonNull
    public JwtAuthenticationToken convert(@NonNull Jwt jwt) {

        String userId = jwt.getId();

        // 🔥 Aquí devolvemos el token REAL como credentials
        return new JwtAuthenticationToken(
                jwt,   // <<--- ESTA ES LA CLAVE
                Collections.emptyList(),
                userId
        );
    }
}


