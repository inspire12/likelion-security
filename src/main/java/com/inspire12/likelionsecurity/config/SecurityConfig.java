package com.inspire12.likelionsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
//@EnableWebSecurity // 3.x 부터 자동처리
public class SecurityConfig {

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl(); // 메모리에 저장하는 기본 구현체
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // DaoAuthenticationProvider
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/security/signup").permitAll() // /login /signup 은 허가를 해준다
                        .anyRequest().authenticated() // 나머지는 다 인증이 필요하다
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults()) // 세션 기반 로그인 폼 제공
                .sessionManagement(session -> session
                        .maximumSessions(2)
                        .maxSessionsPreventsLogin(false)
                        .sessionRegistry(sessionRegistry())
                )

//                .httpBasic(Customizer.withDefaults())

                .logout(Customizer.withDefaults());   // 로그아웃 시 세션 삭제
        return http.build();
    }
}
