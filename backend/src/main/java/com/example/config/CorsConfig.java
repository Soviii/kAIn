package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CorsConfig sets up global CORS (Cross-Origin Resource Sharing) configuration for the application.
 *
 * This configuration allows HTTP requests from a specific origin (e.g., your frontend running on http://localhost:3000)
 * to access your Spring Boot backend APIs without triggering browser CORS errors.
 *
 * Key configuration details:
 * - Applies CORS rules to all endpoints ("**").
 * - Allows requests only from http://localhost:3000.
 * - Permits GET, POST, PUT, DELETE, and OPTIONS methods.
 * - Accepts any HTTP headers from the client.
 * - Supports credentials (e.g., cookies, authorization headers).
 *
 * Usage:
 * Add this class to your Spring Boot application. On startup, the settings will apply automatically.
 *
 */
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*") 
                    .allowCredentials(true);
            }
        };
    }
}
