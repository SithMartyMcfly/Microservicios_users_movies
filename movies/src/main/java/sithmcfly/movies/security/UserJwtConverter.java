package sithmcfly.movies.security;

import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.lang.NonNull;

public class UserJwtConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Override
    @NonNull
    public UsernamePasswordAuthenticationToken convert(@NonNull Jwt jwt) {
        // Recogemos el ID del token
        String userId = jwt.getId();

        return new UsernamePasswordAuthenticationToken(
                userId,
                jwt.getTokenValue(),   // 🔥 Aquí va el token REAL
                Collections.emptyList()
        );
    }
}