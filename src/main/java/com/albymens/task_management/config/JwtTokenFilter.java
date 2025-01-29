package com.albymens.task_management.config;

import com.albymens.task_management.service.JwtTokenProviderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@WebFilter("/*")
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenProviderService jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProviderService jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;  // Ensure the constructor accepts the provider
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("AUTHORIZATION");
        if(token != null && token.startsWith("Bearer ")){
            token = token.substring(7);
            if(jwtTokenProvider.validateToken(token)){
                String username = jwtTokenProvider.getUsername(token);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        username, null, null
                ));
            }
        }
        filterChain.doFilter(request, response);
    }
}
