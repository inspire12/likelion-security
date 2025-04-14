package com.inspire12.likelionsecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Component
public class CustomSecurityFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public CustomSecurityFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if ("likelion".equals(request.getHeader("Authorization"))) {
                filterChain.doFilter(request, response);
            }
            throw new AuthenticationException();
        } catch (AuthenticationException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
