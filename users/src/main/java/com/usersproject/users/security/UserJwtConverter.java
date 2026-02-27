package com.usersproject.users.security;

import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.lang.NonNull;

public class UserJwtConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Override
    @NonNull
    public UsernamePasswordAuthenticationToken convert(@NonNull Jwt jwt) {

        String userId = jwt.getId();

        // 🔥 Aquí devolvemos el token REAL como credentials
        return new UsernamePasswordAuthenticationToken(
                userId,
                jwt.getTokenValue(),   // <<--- ESTA ES LA CLAVE
                Collections.emptyList()
        );
    }
}


