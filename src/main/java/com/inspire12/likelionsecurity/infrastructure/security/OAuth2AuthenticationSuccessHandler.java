package com.inspire12.likelionsecurity.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private Logger log = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);
    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String token = jwtTokenProvider.generateTokenByOauth2(authentication);

        log.info("Successfully authenticated user: {}", authentication.getName());
        // JWT를 쿼리 파라미터 등으로 클라이언트에 전달하거나 헤더에 넣어 응답함.
        response.sendRedirect("http://localhost/oauth2/redirect?token=" + token);
    }
}
