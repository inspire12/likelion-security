package com.inspire12.likelionsecurity.config;

import com.inspire12.likelionsecurity.infrastructure.security.CustomAuthenticationEntryPoint;
import com.inspire12.likelionsecurity.infrastructure.security.CustomOAuth2UserService;
import com.inspire12.likelionsecurity.infrastructure.security.JwtTokenProvider;
import com.inspire12.likelionsecurity.infrastructure.security.OAuth2AuthenticationSuccessHandler;
import com.inspire12.likelionsecurity.infrastructure.security.filter.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
//@EnableWebSecurity // 3.x 부터 자동처리되어 필요없음
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final JwtFilter jwtFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(JwtFilter jwtFilter, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, CustomOAuth2UserService customOAuth2UserService) {
        this.jwtFilter = jwtFilter;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable) // ui (로그인 폼)을 제공하는 방식
                .httpBasic(AbstractHttpConfigurer::disable) // 인증할 때마다 인증정보(Authorization: Basic {base64 encoded id:password} 형태로 보냄)를
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)   // CSRF 공격 방지 설정을 비활성화 (JWT와 같이 Stateless 방식일 때는 일반적으로 비활성화함)
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2AuthenticationSuccessHandler())
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/security/**", "/api/security/login", "/api/security/signup", "/actuator/**").permitAll() // /login /signup 은 허가를 해준다
                        .requestMatchers(HttpMethod.POST, "/api/security/**", "/api/security/login", "/api/security/signup").permitAll() // /login /signup 은 허가를 해준다
                        .requestMatchers(HttpMethod.OPTIONS, "/api/security/**", "/api/security/login", "/api/security/signup").permitAll() // /login /signup 은 허가를 해준다
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated() // 나머지는 다 인증이 필요하다
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
// 인증이 되지 않으면 login 페이지로 넘긴다 --> token이면 프론트에서 처리
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(jwtTokenProvider);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

