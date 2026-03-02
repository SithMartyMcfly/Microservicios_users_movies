package sithmcfly.movies.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 🔥🔥🔥 LOG PARA VER SI LLEGA EL HEADER AUTH 🔥🔥🔥
        http.addFilterBefore((request, response, chain) -> {

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            // TENEMOS TOKEN AQUI
            System.out.println("El TOKEN ES ESTE " + ((HttpServletRequest) request).getHeader("Authorization"));


            chain.doFilter(request, response);
        }, org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class);

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/movies/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec("perroperroperroperroperroperroperro".getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }
}

