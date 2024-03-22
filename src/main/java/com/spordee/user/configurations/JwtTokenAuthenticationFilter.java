package com.spordee.user.configurations;

import com.spordee.user.exceptions.OAuth2AuthenticationProcessingException;
import com.spordee.user.util.CommonMethods;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class JwtTokenAuthenticationFilter implements WebFilter {
    public static final String HEADER_PREFIX = "Bearer ";

//    @Value("${jwt.tokenDecryptCode}")
    private  String tokenDecryptCode ="kfsdSDFsdfmflks32FSDskfdskfsdSDFsdfmflks32FSDskfdskfsdSDFsdfmflks32FSDskfdskfsdSDFsdfmflks32FSDskfds";

    private final JwtTokenProvider tokenProvider;

//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .csrf()
//                .csrfTokenRepository(csrfTokenRepository())
//                .requireCsrfProtectionMatcher(ServerWebExchangeMatchers.pathMatchers("/secured/**"))
//                .and()
//                .build();
//    }
//
//    @Bean
//    public ServerCsrfTokenRepository csrfTokenRepository() {
//        CookieServerCsrfTokenRepository repository = CookieServerCsrfTokenRepository.withHttpOnlyFalse();
//        repository.setCookieName("XSRF-TOKEN"); // Customize the CSRF cookie name if needed
//        return repository;
//    }
    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());
       String jwtToken = tokenDecryption(token);
        if (StringUtils.hasText(jwtToken) && this.tokenProvider.validateToken(jwtToken)) {
            return Mono.fromCallable(() -> this.tokenProvider.getAuthentication(jwtToken))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(authentication -> chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
        }
        return chain.filter(exchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public  String tokenDecryption(String encryptedToken) {
        byte[] keyBytes = tokenDecryptCode.getBytes();
        byte[] validKeyBytes = new byte[32]; // AES-256 key length
        System.arraycopy(keyBytes, 0, validKeyBytes, 0, Math.min(keyBytes.length, validKeyBytes.length));
        Key key = new SecretKeySpec(validKeyBytes, "AES");
        Cipher cipher;
        try {
            byte[] ivAndEncryptedTokenBytes = Base64.getDecoder().decode(encryptedToken);
            byte[] ivBytes = Arrays.copyOfRange(ivAndEncryptedTokenBytes, 0, 16); // Extract the IV from the encrypted data
            byte[] encryptedTokenBytes = Arrays.copyOfRange(ivAndEncryptedTokenBytes, 16, ivAndEncryptedTokenBytes.length);

            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivBytes);
            cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);
            byte[] decryptedTokenBytes = cipher.doFinal(encryptedTokenBytes);
            return new String(decryptedTokenBytes);
        } catch (Exception e) {
            throw new OAuth2AuthenticationProcessingException(e.getMessage());
        }
    }

}
