package com.inspire12.likelionsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity // 3.x 부터 자동처리
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signup").permitAll() // /login /signup 은 허가를 해준다
                        .anyRequest().authenticated() // 나머지는 다 인증이 필요하다
                )
                .formLogin(Customizer.withDefaults()) // 세션 기반 로그인 폼 제공
                .logout(Customizer.withDefaults());   // 로그아웃 시 세션 삭제
        return http.build();
    }
}
