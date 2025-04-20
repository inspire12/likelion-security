package com.inspire12.likelionsecurity.controller;

import com.inspire12.likelionsecurity.service.SessionCheckService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/security")
@RestController
public class SecurityApiController {

    private final SessionCheckService sessionCheckService;

    public SecurityApiController(SessionCheckService sessionCheckService) {
        this.sessionCheckService = sessionCheckService;
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }

    @GetMapping("/sessions-info")
    public List<Object> sessionsInfo() {
        return sessionCheckService.getAllPrincipals();
    }

    @GetMapping("/session-info")
    public String sessionInfo(HttpSession session) {
        String username = sessionCheckService.setSessionInfo(session);
        return "Session Username: " + username;
    }

    @GetMapping("/session-auth")
    public String sessionAuth(HttpSession session) {
        String username = sessionCheckService.getAuth();
        // 인증 정보 가져오기
        return "Session Username: " + username;
    }
}
