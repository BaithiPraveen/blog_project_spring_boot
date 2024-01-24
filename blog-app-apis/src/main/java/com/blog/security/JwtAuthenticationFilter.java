package com.blog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;


import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtAuthenticationProvider authenticationProvider;

    @Autowired
    UserDetailsService userDetailsService;

    /**
     * Filters and processes the incoming HTTP request for JWT authentication.
     * Extracts the JWT token, validates it, and sets authentication details in the SecurityContextHolder.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @param filterChain The filter chain for processing the request.
     * @throws ServletException If an exception occurs during servlet processing.
     * @throws IOException      If an I/O exception occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = getTokenFromRequest(request);
        if (StringUtils.hasText(jwtToken) && authenticationProvider.validateToken(jwtToken)){
            String userName = authenticationProvider.getUserName(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request,response);
    }

    /**
     * Extracts the JWT token from the Authorization header in the HTTP request.
     *
     * @param request The HTTP request object.
     * @return The extracted JWT token or null if not present.
     */
    private String getTokenFromRequest(HttpServletRequest request) {

       String bearerToken =request.getHeader("Authorization");
       if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
           return bearerToken.substring(7);
       else return null;
    }
}
