package com.usersproject.users.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

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
    public UsernamePasswordAuthenticationToken convert (Jwt jwt){
        // Recuperamos el id que trae JWT
        String userId = jwt.getId();
        // Retornamos el token de autenticación pasandole el parámetro de Id
        return new UsernamePasswordAuthenticationToken(userId, null, null);
    }  
}
