package com.darm.ifce.achadoseperdidos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.darm.ifce.achadoseperdidos.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        public SecurityConfig(
                        JwtAuthenticationFilter jwtAuthFilter,
                        AuthenticationProvider authenticationProvider) {

                this.jwtAuthFilter = jwtAuthFilter;
                this.authenticationProvider = authenticationProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests((auth) -> auth
                                                .requestMatchers(HttpMethod.POST, "api/v1/auth/login").permitAll()
                                                .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                                                .requestMatchers("/api/v1/auth/register-admin").hasRole("ADMIN")
                                                .requestMatchers("/api/v1/persons").hasAnyRole("ADMIN","USER")
                                                .requestMatchers(HttpMethod.DELETE,"/api/v1/persons").hasRole("ADMIN")
                                                .requestMatchers("/api/v1/thing").hasAnyRole("ADMIN","USER")
                                                .requestMatchers("/api/v1/thing/user").hasAnyRole("ADMIN","USER")
                                                .requestMatchers(HttpMethod.DELETE,"/api/v1/thing").hasRole("ADMIN")
                                                .requestMatchers("/api/v1/comment").hasAnyRole("ADMIN","USER")
                                                .requestMatchers("/api/v1/comment/thing/").hasAnyRole("ADMIN","USER")
                                                .requestMatchers(HttpMethod.DELETE,"/api/v1/comment").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }
}
