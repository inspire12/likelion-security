package com.inspire12.likelionsecurity.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/security")
@RestController
public class SecurityApiController {

    private final SessionRegistry sessionRegistry;

    public SecurityApiController(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }

    @GetMapping("/sessions-info")
    public List<Object>  sessionsInfo() {
        return sessionRegistry.getAllPrincipals();
    }

        @GetMapping("/session-info")
    public String sessionInfo(HttpSession session) {
        // 세션에서 특정 속성 가져오기
        String username = (String) session.getAttribute("username");
        // 세션에 데이터 저장하기
        session.setAttribute("customData", "Hello, Session!");
        return "Session Username: " + username;
    }

    @GetMapping("/session-auth")
    public String sessionAuth(HttpSession session) {
        // 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean hasRoleAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("USER"));
        return "Session Username: " + username;
    }
}
