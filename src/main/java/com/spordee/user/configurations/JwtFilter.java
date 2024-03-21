package com.spordee.user.configurations;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.spordee.user.tokenmapper.TokenIntrospectionResponse;
import com.spordee.user.tokenmapper.objects.Role;
import com.spordee.user.util.CommonMethods;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    private final CommonMethods commonMethods;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = commonMethods.tokenDecryption(authHeader.substring(7));
            username = jwtUtil.extractUsername(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = getUserDetails(token,response);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    public UserDetails getUserDetails(String token, HttpServletResponse response) throws JsonProcessingException {
        TokenIntrospectionResponse userDetails = new TokenIntrospectionResponse();
        Claims claims = JwtUtil.extractAllClaims(token);
        String userName = (String) claims.get(Claims.SUBJECT);
        List<Role> roles = JwtUtil.extractRolesFromToken(token,response);
        userDetails.setRoles(roles);
        userDetails.setUserName(userName);
        return userDetails;
    }

}