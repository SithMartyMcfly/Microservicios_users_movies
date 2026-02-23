package sithmcfly.movies.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    public FeignClientInterceptor() {
        System.out.println("🔥🔥🔥 INTERCEPTOR REGISTRADO POR SPRING 🔥🔥🔥");
    }

    @Override
    public void apply(RequestTemplate template) {
        System.out.println("🔥 FEIGN INTERCEPTOR EJECUTADO 🔥");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            System.out.println("TOKEN EN CREDENTIALS: " + authentication.getCredentials());
        } else {
            System.out.println("NO HAY AUTHENTICATION");
        }
    }
}


