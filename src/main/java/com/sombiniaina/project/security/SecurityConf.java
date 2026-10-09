package com.sombiniaina.project.security;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConf {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/ping", "/health/**", "/products/new-arrivals")
                    .permitAll()
                    .requestMatchers(HttpMethod.PUT, "/admin/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated())
        .httpBasic(withDefaults());
    return http.build();
  }
}
