package com.fueltrack.platform.interfaces.rest;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Global CORS configuration.
 *
 * <p>Provides a {@link CorsConfigurationSource} bean that Spring Security's
 * {@code .cors(Customizer.withDefaults())} picks up automatically.  We intentionally
 * do <strong>not</strong> implement {@code WebMvcConfigurer} to avoid dual CORS
 * processing that can cause preflight requests to be rejected.</p>
 */
@Configuration
public class WebCorsConfig {

    private final List<String> allowedOrigins;

    /**
     * Creates a new CORS configuration.
     *
     * @param allowedOriginsCsv allowed origins configured in application properties
     */
    public WebCorsConfig(@Value("${app.cors.allowed-origins}") String allowedOriginsCsv) {
        this.allowedOrigins = Arrays.stream(allowedOriginsCsv.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isBlank())
                .toList();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply to ALL paths so that Swagger, actuator, etc. also get CORS headers.
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
