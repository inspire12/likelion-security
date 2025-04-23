package com.inspire12.likelionsecurity.presentation.controller;

import com.inspire12.likelionsecurity.application.service.AuthenticationService;
import com.inspire12.likelionsecurity.application.service.PasswordResetService;
import com.inspire12.likelionsecurity.presentation.dto.PasswordResetRequest;
import com.inspire12.likelionsecurity.presentation.dto.request.LoginRequest;
import com.inspire12.likelionsecurity.presentation.dto.request.SignupRequest;
import com.inspire12.likelionsecurity.presentation.dto.response.LoginResponse;
import com.inspire12.likelionsecurity.presentation.dto.response.SignupResponse;
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
    private final PasswordResetService passwordResetService;

    public SecurityController(AuthenticationService authenticationService, PasswordResetService passwordResetService) {
        this.authenticationService = authenticationService;
        this.passwordResetService = passwordResetService;
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

    // 1) 이메일 입력 → 토큰 발송
    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestReset(@RequestBody PasswordResetRequest passwordResetRequest) {
        passwordResetService.createPasswordResetToken(passwordResetRequest.getEmail());
        return ResponseEntity.ok("이메일로 재설정 링크를 보냈습니다.");
    }

    // 2) 토큰+새 비밀번호 → 비밀번호 변경
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        passwordResetService.resetPassword(passwordResetRequest.getToken(), passwordResetRequest.getPassword());
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

}