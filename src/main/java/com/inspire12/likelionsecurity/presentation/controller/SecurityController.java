package com.inspire12.likelionsecurity.presentation.controller;

import com.inspire12.likelionsecurity.application.service.AuthenticationService;
import com.inspire12.likelionsecurity.presentation.controller.dto.request.SignupRequest;
import com.inspire12.likelionsecurity.presentation.controller.dto.response.SignupResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/security")
@RestController
public class SecurityController {
    private final AuthenticationService authenticationService;

    public SecurityController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        try {
            SignupResponse response = authenticationService.signup(signupRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    SignupResponse.failSignupResponse
            );
        }
    }
}