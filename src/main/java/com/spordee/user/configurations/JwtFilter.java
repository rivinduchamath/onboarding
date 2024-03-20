package com.spordee.user.configurations;


import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.spordee.user.exceptions.OAuth2AuthenticationProcessingException;
import com.spordee.user.tokenmapper.TokenIntrospectionResponse;
import com.spordee.user.tokenmapper.objects.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.util.*;

import static com.spordee.user.util.AttributesCommon.JWT_HEADER_AUTH_ROLES;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Value("${jwt.secret}")
    private String secret;


    @Value("${jwt.tokenDecryptCode}")
    private String tokenDecryptCode;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = tokenDecryption(authHeader.substring(7));
            username = jwtUtil.extractUsername(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = getUserDetails(token);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    public UserDetails getUserDetails(String token) {
        TokenIntrospectionResponse userDetails = new TokenIntrospectionResponse();
        Claims claims = jwtUtil.extractAllClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);

        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_GUEST"));
        userDetails.setRoles(roles);
        userDetails.setUserName(subject);
        return userDetails;
    }

    public String tokenDecryption(String encryptedToken) {
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