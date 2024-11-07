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

                                .requestMatchers(HttpMethod.GET, "/appointments").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/appointments").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/appointments/{appointmentId}").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/appointments/complete/{appointmentId}").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.DELETE, "/appointments/{appointmentId}").hasAuthority("ROLE_USER")

                                .requestMatchers(HttpMethod.GET, "/hairdressers").permitAll()
                                .requestMatchers(HttpMethod.GET, "/hairdressers/{hairdresserId}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/hairdressers").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/hairdressers/{hairdresserId}").hasAuthority("ROLE_HAIRDRESSER")
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

                                .anyRequest().permitAll()
                )
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(authEntryPoint))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.disable())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
