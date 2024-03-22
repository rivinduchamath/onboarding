package com.spordee.user.configurations;

import com.spordee.user.configurations.Entity.SpordUser;
import com.spordee.user.tokenmapper.objects.Role;
import com.spordee.user.util.AttributesCommon;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

import static java.util.stream.Collectors.joining;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String AUTHORITIES_KEY = "roles";
    private static final String DEVICE_ID = "JWT_DEVICE_ID";
    private static final String DEVICE = "DEVICE_NAME";
    private static final String AUTH_ID = "sub";


    private String secretKey = "04ca023b39512e46d0c2cf4b48d5aac61d34302994c87ed4eff225dcf3b0a218739f3897051a057f9b846a69ea2927a587044164b7bae5e1306219d50b588cb1";


//    @PostConstruct
//    public void init() {
//        var secret = Base64.getEncoder()
//                .encodeToString(this.jwtProperties.getSecretKey().getBytes());
//        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//    }



    public Authentication getAuthentication(String token) {
        Claims claims =Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
        JwsHeader header =Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getHeader();

        Object authoritiesClaim = extractRolesFromToken(token);
        String deviceId = (String) header.get(DEVICE_ID);
        String device = (String)header.get(DEVICE);

        Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null
                ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        SpordUser principal = new SpordUser(claims.getSubject(), "", authorities,deviceId,device);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Claims claims =Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
            // parseClaimsJws will check expiration date. No need do here.
            log.info("expiration date: {}", claims.getExpiration());
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token: {}", e.getMessage());
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }

    public List<Role> extractRolesFromToken(String token) {
        Key signingKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        try {

//            Claims claims =Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
            JwsHeader header = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getHeader();

            if (header.containsKey(AttributesCommon.JWT_HEADER_AUTH_ROLES)) {
                List<Map<String, String>> roleMaps = (List<Map<String, String>>) header.get(AttributesCommon.JWT_HEADER_AUTH_ROLES);
                List<Role> roles = new ArrayList<>();

                for (Map<String, String> roleMap : roleMaps) {
                    String authority = roleMap.get("authority");
                    if (authority != null && !authority.isEmpty()) {
                        roles.add(new Role(authority));
                    } else {
                        // Handle invalid or missing role authority
                        // You can log a warning or perform other actions here
                    }
                }
                return roles;
            } else {
                // Handle case where JWT does not contain roles information
                // You can log a warning or perform other actions here
            }
        } catch (JwtException | IllegalArgumentException e) {
            // Handle JWT parsing or validation exceptions
            e.printStackTrace(); // Or log the exception
            // You can throw a custom exception or return an empty list here based on your requirement
        }
        return Collections.emptyList(); // Return an empty list if roles cannot be extracted
    }

}
