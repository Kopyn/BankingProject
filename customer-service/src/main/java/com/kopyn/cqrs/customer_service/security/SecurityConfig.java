package com.kopyn.cqrs.customer_service.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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

                        .pathMatchers(HttpMethod.POST, "/customers/**")
                        .hasAuthority("SCOPE_write")

                        .pathMatchers(HttpMethod.PUT, "/customers/**")
                        .hasAuthority("SCOPE_update")

                        .pathMatchers(HttpMethod.DELETE, "/customers/**")
                        .hasAuthority("SCOPE_delete")

                        .pathMatchers(HttpMethod.GET, "/customers/**")
                        .hasAuthority("SCOPE_read")

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> {})
                );

        return http.build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("keycloak", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("http://localhost:8080/realms/myrealm/protocol/openid-connect/auth")
                                                .tokenUrl("http://localhost:8080/realms/myrealm/protocol/openid-connect/token")
                                        )
                                )
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("keycloak"));
    }
}
