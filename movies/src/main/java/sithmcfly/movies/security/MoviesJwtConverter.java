package sithmcfly.movies.security;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.lang.NonNull;

public class MoviesJwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    @NonNull
    public JwtAuthenticationToken convert(@NonNull Jwt jwt) {
        // Recogemos el Role del token
        String role = jwt.getClaimAsString("role");
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));


        return new JwtAuthenticationToken(
                jwt, //<-------------Token
                authorities, //<-----Rol
                jwt.getSubject() //<-ID USUARIO
        );
    }
}