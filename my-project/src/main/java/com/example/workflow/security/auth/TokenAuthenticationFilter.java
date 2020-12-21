package com.example.workflow.security.auth;

import com.example.workflow.security.TokenUtils;
import com.example.workflow.services.SystemUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;

    private SystemUserService userDetailsService;

    public TokenAuthenticationFilter(TokenUtils tokenHelper, SystemUserService userDetailsService) {
        this.tokenUtils = tokenHelper;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String username;
        String authToken = tokenUtils.getToken(request);

        if(authToken != null){
            username = tokenUtils.getEmailFromToken(authToken);

            if(username != null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (tokenUtils.validateToken(authToken, userDetails)) {
                    // kreiraj autentifikaciju
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}