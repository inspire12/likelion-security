package com.inspire12.likelionsecurity.application.service;

import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;
import com.inspire12.likelionsecurity.infrastructure.memoryrepository.UserMemoryRepository;
import com.inspire12.likelionsecurity.presentation.controller.dto.request.SignupRequest;
import com.inspire12.likelionsecurity.presentation.controller.dto.response.SignupResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignupService {

    private final UserMemoryRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupService(UserMemoryRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        List<? extends GrantedAuthority> grantedAuthorities = signupRequest.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
        if (signupRequest.getRoles().isEmpty()) {
            grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        }
        UserEntity user = new UserEntity(signupRequest.getUsername(), encodedPassword, grantedAuthorities);
        UserEntity userSaved = userRepository.save(user);
        return new SignupResponse(userSaved.getUsername(), "가입 성공", userSaved.getRolesGranted());
    }
}