package sithmcfly.movies.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore((request, response, chain) -> {

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            chain.doFilter(request, response);
        }, org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class);

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Endpoints solo accesibles a ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/api/movies/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/movies/{id}").hasRole("ADMIN")
                        // Resto enpoints son con autenticación
                        .requestMatchers("/api/movies/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new MoviesJwtConverter())));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec("perroperroperroperroperroperroperro".getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }
}

