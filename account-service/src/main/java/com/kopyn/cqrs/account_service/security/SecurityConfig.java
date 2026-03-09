package com.kopyn.cqrs.account_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()

                        .pathMatchers(HttpMethod.POST, "/accounts/**")
                        .hasAuthority("SCOPE_write")

                        .pathMatchers(HttpMethod.PUT, "/accounts/**")
                        .hasAuthority("SCOPE_update")

                        .pathMatchers(HttpMethod.DELETE, "/accounts/**")
                        .hasAuthority("SCOPE_delete")

                        .pathMatchers(HttpMethod.GET, "/accounts/**")
                        .hasAuthority("SCOPE_read")

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> {})
                );

        return http.build();
    }
}
