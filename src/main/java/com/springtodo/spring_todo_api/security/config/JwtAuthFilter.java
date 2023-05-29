package com.springtodo.spring_todo_api.security.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.annotation.Nonnull;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(

            @Nonnull HttpServletRequest request,

            @Nonnull HttpServletResponse response,

            @Nonnull FilterChain filterChain

    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization"); // header that contains the jwt token.

        final String jwtToken;

        final String userEmail;

        // checking if we have the jwt token?

        if (authHeader == null || !authHeader.startsWith("Bearer ")) { // The build token should be always start with
                                                                       // keyword 'Bearer '

            filterChain.doFilter(request, response);

            return;

        }

        // How to extract token string from Bearer token?

        jwtToken = authHeader.substring(7); // Extract jwt token from header

        userEmail = jwtService.extractUserName(jwtToken); // todo extract userEmail from jwt token;

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(

                        userDetails, null, userDetails.getAuthorities()

                );

                authToken.setDetails(

                        new WebAuthenticationDetailsSource().buildDetails(request)

                );

                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }

        filterChain.doFilter(request, response);

    }

}
