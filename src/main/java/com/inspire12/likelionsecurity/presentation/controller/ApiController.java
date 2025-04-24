package com.inspire12.likelionsecurity.presentation.controller;

import com.inspire12.likelionsecurity.application.service.AuthenticationService;
import com.inspire12.likelionsecurity.presentation.controller.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiController {

    private final AuthenticationService authenticationService;

    public ApiController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> getMe(Principal principal) {
        String name = principal.getName();
        LoginResponse response = new LoginResponse("", name, List.of());
        return ResponseEntity.ok().body(response);
    }
}