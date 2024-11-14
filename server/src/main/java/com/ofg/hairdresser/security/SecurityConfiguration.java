package com.ofg.hairdresser.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private final TokenFilter tokenFilter;
    private final AuthEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfiguration(TokenFilter tokenFilter, AuthEntryPoint authEntryPoint) {
        this.tokenFilter = tokenFilter;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authentication) ->
                        authentication
                                .requestMatchers(HttpMethod.PUT, "/admin/approve/hairdressers/{hairdresserId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/admin/hairdressers/inactives").hasAuthority("ROLE_ADMIN")

                                .requestMatchers(HttpMethod.GET, "/appointments").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/appointments").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/appointments/{appointmentId}").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/appointments/complete/{appointmentId}").hasAuthority("ROLE_HAIRDRESSER")
                                .requestMatchers(HttpMethod.DELETE, "/appointments/{appointmentId}").hasAuthority("ROLE_USER")

                                .requestMatchers(HttpMethod.GET, "/availabilities/{hairdresserId}").permitAll()

                                .requestMatchers(HttpMethod.GET, "/hairdressers").permitAll()
                                .requestMatchers(HttpMethod.GET, "/hairdressers/{hairdresserId}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/hairdressers").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/hairdressers/{hairdresserId}").hasAuthority("ROLE_HAIRDRESSER")
                                .requestMatchers(HttpMethod.PUT, "/hairdressers/{hairdresserId}/update-main-image").hasAuthority("ROLE_HAIRDRESSER")
                                .requestMatchers(HttpMethod.PUT, "/hairdressers/{hairdresserId}/update-side-images").hasAuthority("ROLE_HAIRDRESSER")
                                .requestMatchers(HttpMethod.DELETE, "/hairdressers/{hairdresserId}").hasAuthority("ROLE_HAIRDRESSER")

                                .requestMatchers(HttpMethod.GET, "/reviews/{hairdresserId}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/reviews").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/reviews/{reviewId}").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.DELETE, "/reviews/{reviewId}").hasAuthority("ROLE_USER")

                                .requestMatchers(HttpMethod.GET, "/treatments/hairdresser/{hairdresserId}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/treatments/{treatmentId}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/treatments").hasAuthority("ROLE_HAIRDRESSER")
                                .requestMatchers(HttpMethod.PUT, "/treatments/{treatmentId}").hasAuthority("ROLE_HAIRDRESSER")
                                .requestMatchers(HttpMethod.DELETE, "/treatments/{treatmentId}").hasAuthority("ROLE_HAIRDRESSER")

                                .requestMatchers(HttpMethod.GET, "/roles").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/roles/{roleId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/roles").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/roles/{roleId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/roles/{roleId}").hasAuthority("ROLE_ADMIN")

                                .requestMatchers(HttpMethod.GET, "/roles").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/roles/{roleId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/roles").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/roles/{roleId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/roles/{roleId}").hasAuthority("ROLE_ADMIN")

                                .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/{userId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/users").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/users/{userId}").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/users/activate/{token}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/users/update-password/{userId}").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/users/reset-password").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/users/reset-password/verify/{token}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/users/{userId}").hasAuthority("ROLE_USER")

                                .anyRequest().permitAll()
                )
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(authEntryPoint))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.disable())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(corsConfig -> corsConfig.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setMaxAge(3600L);
                    return config;
                }));
        return http.build();
    }
}
