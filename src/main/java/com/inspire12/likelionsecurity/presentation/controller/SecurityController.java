package com.inspire12.likelionsecurity.presentation.controller;

import com.inspire12.likelionsecurity.application.service.AuthenticationService;
import com.inspire12.likelionsecurity.presentation.controller.dto.request.LoginRequest;
import com.inspire12.likelionsecurity.presentation.controller.dto.request.SignupRequest;
import com.inspire12.likelionsecurity.presentation.controller.dto.response.LoginResponse;
import com.inspire12.likelionsecurity.presentation.controller.dto.response.SignupResponse;
import jakarta.servlet.http.HttpServletRequest;
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
//    @PostMapping("/login")
//    public ResponseEntity<Void> login(//@RequestBody LoginRequest request,
//                                      HttpServletResponse response) {
////        String token = authService.authenticate(request);
//        String token = "likelion";
//        ResponseCookie cookie = ResponseCookie.from("jwt", token)
//                .httpOnly(true)          // JS 접근 차단

    /// /                .secure(true)            // HTTPS만 가능
//                .path("/")
//                .maxAge(Duration.ofDays(7))
//                .sameSite("Strict")      // CSRF 방지 강화
//                .build();
//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> signin(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authenticationService.authenticate(loginRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    LoginResponse.failLoginResponse
            );
        }
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


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok().build();
    }

}