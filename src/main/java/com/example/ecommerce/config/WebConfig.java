package com.example.ecommerce.config;

import com.example.ecommerce.filter.RoleGuardFilter;
import com.example.ecommerce.filter.SessionValidationFilter;
import com.example.ecommerce.middleware.LoggingMiddleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingMiddleware loggingMiddleware;

    @Autowired
    private SessionValidationFilter sessionValidationFilter;

    @Autowired
    private RoleGuardFilter roleGuardFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Logs every request globally
        registry.addInterceptor(loggingMiddleware);

        // Session/token validation for all protected routes
        registry.addInterceptor(sessionValidationFilter)
                .addPathPatterns("/student/**", "/registration/**", "/api/db/**", "/api/performance/**")
                .excludePathPatterns("/student/dashboard", "/student/profile/save", "/api/payment/**");

        // Role guard — Admin and Lecturer only for database routes
        registry.addInterceptor(roleGuardFilter)
                .addPathPatterns("/api/db/**");
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringAntMatchers("/api/**", "/auth/login")
                )
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()
                )
                .headers().frameOptions().disable();
        return http.build();
    }
}