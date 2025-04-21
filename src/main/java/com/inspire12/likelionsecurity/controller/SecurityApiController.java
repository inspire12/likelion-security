package com.inspire12.likelionsecurity.controller;

import com.inspire12.likelionsecurity.dto.LoginRequest;
import com.inspire12.likelionsecurity.dto.SignupRequest;
import com.inspire12.likelionsecurity.service.SessionCheckService;
import com.inspire12.likelionsecurity.service.SignupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/security")
@RestController
public class SecurityApiController {

    private final Logger log = LoggerFactory.getLogger(SecurityApiController.class);
    private final SessionCheckService sessionCheckService;
    private final SignupService signupService;

    public SecurityApiController(SessionCheckService sessionCheckService, SignupService signupService) {
        this.sessionCheckService = sessionCheckService;
        this.signupService = signupService;
    }

    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest, HttpServletRequest request) {
    public ResponseEntity<?> signup(@ModelAttribute SignupRequest signupRequest, //application/x-www-form-urlencoded
                                    HttpServletRequest request) {
        log.info("signup request received");
        signupService.register(signupRequest, request);
        return ResponseEntity.ok("회원가입 및 로그인 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        log.info("signup request received");
        signupService.signin(loginRequest, request);
        return ResponseEntity.ok("회원가입 및 로그인 완료");
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
