package com.javaschool.onlineshop.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {


    // Autowiring the JWTGenerator and CustomUserDetailService for dependency injection
    @Autowired
    private JWTGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailService customUserDetailsService;

    // This method is called for each incoming request and processes JWT authentication
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extracting JWT token from the request
        String token = getJWTFromRequest(request);

        // Validating the token and extracting the username from it
        if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
            String userName = tokenGenerator.getUserNameFromJWT(token);

            // Loading user details from the database based on the username
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

            // Creating an authentication token and setting it in the SecurityContextHolder
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Proceeding to the next filter in the chain
        filterChain.doFilter(request, response);
    }

    // This method extracts the JWT token from the "Authorization" header in the request
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
