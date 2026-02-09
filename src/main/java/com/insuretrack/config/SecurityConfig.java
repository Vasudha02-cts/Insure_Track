package com.insuretrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for Postman testing
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow all requests without login
                );

        return http.build();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabled for testing with Postman
                .authorizeHttpRequests(auth -> auth
                        // 1. Public Endpoints (Anyone can access)
                        .requestMatchers("/api/auth/**", "/api/users/register/**").permitAll()

                        // 2. Admin Only (e.g., viewing all Audit Logs)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 3. Underwriter Only (e.g., approving policies)
                        .requestMatchers("/api/policies/approve/**").hasRole("UNDERWRITER")

                        // 4. Agent/Underwriter (e.g., creating quotes)
                        .requestMatchers("/api/quotes/**").hasAnyRole("AGENT", "UNDERWRITER")

                        // 5. Customer (e.g., viewing their own profile)
                        .requestMatchers("/api/customers/me").hasRole("CUSTOMER")

                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Uses Basic Auth for Postman testing

        return http.build();
    }
}