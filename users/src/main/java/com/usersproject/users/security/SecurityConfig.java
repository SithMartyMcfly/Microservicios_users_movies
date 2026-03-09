package com.usersproject.users.security;


import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        // configuramos el objeto HttpSecurity
        http
            // Desabilitamos porque no vamos a trabajar con sesiones
            .csrf(csrf -> csrf.disable())
            // Configuración de peticiones que van con autenticación
            .authorizeHttpRequests(auth -> auth
                    // Rutas permitidas para entrar sin Autenticación
                    .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users/create").permitAll()
                    // Rutas permitidas para ADMIN
                    .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/users/list").hasRole("ADMIN")
                    // Rutas permitidas para usuarios registrados (con @Preauthorized en controlador)
                    .requestMatchers(HttpMethod.GET, "/api/users/{id}").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/api/users/{id}").authenticated()
                // El resto de rutas necesitan Autenticación
                .anyRequest().authenticated()    
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                // Vamos a usar un token de tipo JWT
                .jwt(jwt -> jwt
                    // Le decimos como va a convertir JWT usando
                    // nuestra clase UserJwtConverter
                    .jwtAuthenticationConverter(new UserJwtConverter())
                )
            );
        // Devuelve y construye la cadena con los filtros que usará Spring en cada request
        return http.build();
    }

    
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec("perroperroperroperroperroperroperro".getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }
 
}
