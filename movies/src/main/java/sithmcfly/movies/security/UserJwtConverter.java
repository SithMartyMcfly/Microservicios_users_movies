package sithmcfly.movies.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;

/* En resumen:
 *
 * - Spring valida el token
 * - Spring crea un Jwt
 * - Spring llama a este convertidor
 * - Este convertidor crea un Authentication
 * - Spring guarda ese Authentication en el SecurityContext
 *
 * Así conseguimos autenticación basada en JWT sin filtros manuales.
 */

public class UserJwtConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    @Override
    @NonNull
    public UsernamePasswordAuthenticationToken convert (@NonNull Jwt jwt){
        // Recupera el id que trae JWT
        String userId = jwt.getId();
        // Retornamos el token de autenticación pasando el parámetro de Id
        return new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
    }  
}
