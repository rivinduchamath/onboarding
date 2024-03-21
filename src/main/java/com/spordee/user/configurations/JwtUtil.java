package com.spordee.user.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.response.common.CommonResponse;
import com.spordee.user.tokenmapper.objects.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.spordee.user.exceptions.GlobalExceptionHandler.handleExceptionRoot;
import static com.spordee.user.util.AttributesCommon.JWT_HEADER_AUTH_ROLES;

@Component
@Slf4j
public class JwtUtil {
    private static final String SECRET_KEY ="04ca023b39512e46d0c2cf4b48d5aac61d34302994c87ed4eff225dcf3b0a218739f3897051a057f9b846a69ea2927a587044164b7bae5e1306219d50b588cb1";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
    }
    public static List<Role> extractRolesFromToken(String token, HttpServletResponse response) throws JsonProcessingException {
        Key signingKey = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        log.info("LOG:: JwtUtil extractRolesFromToken");
        try {
            List<Map<String, String>> roleList = (List<Map<String, String>>)
                    Jwts.parserBuilder().setSigningKey(signingKey).build()
                            .parseClaimsJws(token).getHeader().get(JWT_HEADER_AUTH_ROLES);
            List<Role> roles = roleList.stream().map(roleMap ->
                    roleMap.get("authority")).map(Role::new).collect(Collectors.toList());
            log.debug("LOG:: JwtUtil extractRolesFromToken roles");
            return roles;
        }catch (Exception exception){
            log.error("LOG:: JwtUtil extractRolesFromToken Exception");
            handleExceptionRoot(
                    CommonMessages.FORBIDDEN_ACCESS,
                    StatusType.STATUS_FAIL,
                    response,
                    HttpStatus.FORBIDDEN,
                    exception,
                    new CommonResponse(),
                    "Security Error"
            );
        }
        throw new RuntimeException("Security Error");
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}