package com.usersproject.users.security;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.lang.NonNull;

public class UserJwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    @NonNull
    public JwtAuthenticationToken convert(@NonNull Jwt jwt) {
        // Recogemos el rol en un String
        String role = jwt.getClaimAsString("role");
        // Lo convertimos en autoridad para que Spring Security lo entienda, necesita "ROLE_"
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+role));

        return new JwtAuthenticationToken(
                jwt, // <------------TOKEN
                authorities, // <----ROL
                jwt.getSubject() //<-ID USUARIO de esta manera funciona las anotaciones @PreAuthorized
        );
    }
}


