package com.banking.user_account_service.config;

import com.banking.user_account_service.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;

/**
 * @author Keerthana
 **/
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/register", "/api/users/login", "/api/accounts/*/user-id").permitAll()
                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public OncePerRequestFilter jwtAuthFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain)
                    throws ServletException, IOException {
                String path = request.getServletPath();
                if (path.equals("/api/users/login") || path.equals("/api/users/register")) {
                    filterChain.doFilter(request, response);
                    return;
                }
                String header = request.getHeader("Authorization");
                if (header != null && header.startsWith("Bearer ")) {
                    String token = header.substring(7);
                    try {
                        Claims claims = jwtService.parseClaims(token);
                        String subject = claims.getSubject(); // userId as string
                        String email = (String) claims.get("email");

                        // Build an Authentication with minimal authorities for now
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        email, null, Collections.emptyList());

                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } catch (JwtException ex) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                            response.getWriter().write("Invalid or expired token");

                        return;
                    }
                }

                    filterChain.doFilter(request, response);
            }
        };
    }
}
