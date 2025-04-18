package com.inspire12.likelionsecurity.application.service;

import com.inspire12.likelionsecurity.domain.User;
import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;
import com.inspire12.likelionsecurity.infrastructure.memoryrepository.UserMemoryRepository;
import com.inspire12.likelionsecurity.infrastructure.security.CustomUserDetailsService;
import com.inspire12.likelionsecurity.infrastructure.security.JwtTokenProvider;
import com.inspire12.likelionsecurity.infrastructure.security.SignupService;
import com.inspire12.likelionsecurity.presentation.controller.dto.request.LoginRequest;
import com.inspire12.likelionsecurity.presentation.controller.dto.request.SignupRequest;
import com.inspire12.likelionsecurity.presentation.controller.dto.response.LoginResponse;
import com.inspire12.likelionsecurity.presentation.controller.dto.response.SignupResponse;
import com.inspire12.likelionsecurity.support.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMemoryRepository userMemoryRepository;

    public AuthenticationService(JwtTokenProvider jwtTokenProvider,
                                 AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserMemoryRepository userMemoryRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userMemoryRepository = userMemoryRepository;
    }

    public LoginResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        String token = jwtTokenProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new LoginResponse(
                token,
                userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        List<? extends GrantedAuthority> grantedAuthorities = signupRequest.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
        if (signupRequest.getRoles().isEmpty()) {
            grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        }
        String encode = passwordEncoder.encode(signupRequest.getPassword());
        UserEntity user = new UserEntity(signupRequest.getUsername(), encode, grantedAuthorities);
        UserEntity userSaved = userMemoryRepository.save(user);
        return new SignupResponse(userSaved.getUsername(), "가입 성공", userSaved.getRolesGranted());
    }
}
