package com.inspire12.likelionsecurity.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final Key secretKey; // 보안상 별도 관리 필요
    private static final long tokenValidityInMs = 60 * 60 * 100;

    public JwtTokenProvider(@Value("${secret-key}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 생성 메서드
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // 사용자 식별 정보 (주로 username)
                .claim("roles", roles)                  // 권한 정보
                .setIssuedAt(now)                       // 발행 시간
                .setExpiration(validity)                // 만료 시간
                .signWith(this.secretKey)// 서명 알고리즘
                .compact();
    }

    public UserDetails getUserDetails(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        List<String> roles = claims.get("roles", List.class);

        return new CustomUserDetails(username, roles.stream().map(SimpleGrantedAuthority::new).toList());
    }

    public boolean validateToken(String token) {
        if (token != null && getUsername(token) != null && isExpired(token)) {
            return true;
        }
        throw new AuthenticationCredentialsNotFoundException("Invalid token");
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    }
}
