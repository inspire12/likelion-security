package com.inspire12.likelionsecurity.infrastructure.security;

import com.inspire12.likelionsecurity.domain.User;
import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;
import com.inspire12.likelionsecurity.infrastructure.memoryrepository.UserMemoryRepository;
import com.inspire12.likelionsecurity.presentation.controller.dto.request.SignupRequest;
import com.inspire12.likelionsecurity.presentation.controller.dto.response.SignupResponse;

import com.inspire12.likelionsecurity.support.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignupService {
    private final UserMemoryRepository userMemoryRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupService(UserMemoryRepository userMemoryRepository, PasswordEncoder passwordEncoder) {
        this.userMemoryRepository = userMemoryRepository;
        this.passwordEncoder = passwordEncoder;
    }



}