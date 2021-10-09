package com.bestnotes.api.gateway.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http) {
    http
        .csrf().disable()
        .cors(withDefaults())
        .oauth2ResourceServer(o -> o.jwt(withDefaults()))
        .authorizeExchange()
        .pathMatchers("/login/**").permitAll()
        .anyExchange().authenticated();
    return http.build();
  }

}
