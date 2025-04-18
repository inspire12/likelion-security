package com.inspire12.likelionsecurity.infrastructure.memoryrepository;

import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JwtBlacklistRepository {
    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
