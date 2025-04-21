package com.inspire12.likelionsecurity.application.service;

import com.inspire12.likelionsecurity.infrastructure.security.CustomUserDetails;
import com.inspire12.likelionsecurity.presentation.dto.LoginRequest;
import com.inspire12.likelionsecurity.presentation.dto.SignupRequest;
import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;
import com.inspire12.likelionsecurity.infrastructure.memoryrepository.UserMemoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class SignupService {
    private final AuthenticationManager authenticationManager;
    private final UserMemoryRepository userMemoryRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupService(AuthenticationManager authenticationManager, UserMemoryRepository userMemoryRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userMemoryRepository = userMemoryRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void register(SignupRequest signupRequest, HttpServletRequest request) {
        if (userMemoryRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("이미 가입된 아이디입니다.");
        }

        UserEntity user = new UserEntity(signupRequest.getUsername(),
                passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getRoles());
        userMemoryRepository.save(user);

//        // 인증 처리
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), signupRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        // 세션 생성 및 SecurityContext 세션에 저장
//        HttpSession session = request.getSession(true);
//        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
//                SecurityContextHolder.getContext());
    }


    public void signin(LoginRequest signupRequest, HttpServletRequest request) {
        if (!userMemoryRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("가입되지 않은 유저입니다.");
        }
        UserEntity userEntity = userMemoryRepository.get(signupRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(userEntity), signupRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 세션 생성 및 SecurityContext 세션에 저장
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }
}
