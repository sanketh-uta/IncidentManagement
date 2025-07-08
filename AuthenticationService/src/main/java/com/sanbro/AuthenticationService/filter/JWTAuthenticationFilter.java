package com.sanbro.AuthenticationService.filter;

import com.sanbro.AuthenticationService.service.JwtUtility;
import com.sanbro.AuthenticationService.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtility jwtUtility;
    private final UserDetailsServiceImpl userDetailsService;
    public JWTAuthenticationFilter(JwtUtility jwtUtility,UserDetailsServiceImpl userDetailsService){
        this.jwtUtility = jwtUtility;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // I will have to validate the JWT Token and extract the user details and store it in the security context
        if(!request.getRequestURI().equals("/api/auth/login") && !request.getRequestURI().equals("/api/auth/register")){
            log.info("Login API is also hitting");
            // 1. extract the authorization header and token from it
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
               String token = authHeader.substring(7);// remove "Bearer "

                //2 . Extract the username from the token using the utility method
                String username = jwtUtility.extractUserName(token);
                log.info("Extracted username from JWT: {}", username);
                // 3. check if the user is already authenticated if yes then no need to check again
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 4. extract all the user details to create an authentication object
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // 5. check if the token is valid or not
                    if(jwtUtility.validateToken(token,userDetails)){
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(),
                                null,
                                userDetails.getAuthorities()
                        );
                        log.info("WRITING TO THE CONTEXT "+authenticationToken);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }

                }

            }


        }
        filterChain.doFilter(request,response);
    }
}
