package com.spordee.user.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class ApplicationSecurity {
    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http, JwtTokenProvider tokenProvider) {
        final String PATH_POSTS = "/posts/**";

        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(it -> it.pathMatchers(PATH_POSTS).authenticated()
                        .pathMatchers("/me").authenticated().anyExchange().permitAll())
                .addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider),
                        SecurityWebFiltersOrder.HTTP_BASIC).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}