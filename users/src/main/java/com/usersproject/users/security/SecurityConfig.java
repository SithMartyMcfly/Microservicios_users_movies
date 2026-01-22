package com.usersproject.users.security;


import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/users").permitAll()
                // El resto de rutas necesitan Autenticación
                .anyRequest().authenticated()    
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                // Vamos a usar un token de tipo JWT
                .jwt(jwt -> jwt
                    // Le decimos como va a convertir JWT usando
                    // nuestra clase UserHwtConverter
                    .jwtAuthenticationConverter(new UserJwtConverter())
                )
            );
        // Devuelve y construye la cadena con los filtros que usará Spring en cada request
        return http.build();
    }

    // 🔥 ESTE ES EL DECODER — VA AQUÍ MISMO, EN ESTA CLASE
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec("perro".getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }


    
}
