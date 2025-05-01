package com.inspire12.likelionsecurity.infrastructure.memoryrepository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.inspire12.likelionsecurity.infrastructure.security.JwtTokenProvider;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class JwtBlacklistRepository {
    private final Cache<String, Boolean> blacklistCache;

    public JwtBlacklistRepository() {
        this.blacklistCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMillis(JwtTokenProvider.getTokenValidityInMs())) // JWT 유효시간과 맞춰 설정
                .maximumSize(10000) // 최대 캐시 개수 제한
                .build();
    }

    // 블랙리스트에 JWT 등록
    public void blacklistToken(String token) {
        blacklistCache.put(token, true);
    }

    // JWT가 블랙리스트에 있는지 확인
    public boolean isBlacklisted(String token) {
        return blacklistCache.getIfPresent(token) != null;
    }
}
