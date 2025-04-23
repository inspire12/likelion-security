package com.inspire12.likelionsecurity.infrastructure.memoryrepository;


import com.inspire12.likelionsecurity.infrastructure.entity.PasswordResetToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PasswordResetTokenMemoryRepository {

    private final ConcurrentHashMap<String, PasswordResetToken> passwordResetTokenConcurrentHashMap = new ConcurrentHashMap<>();

    public Optional<PasswordResetToken> findByToken(String username) {
        return Optional.of(passwordResetTokenConcurrentHashMap.get(username));
    }

    public void delete(PasswordResetToken passwordResetToken) {
        if (passwordResetToken != null && passwordResetTokenConcurrentHashMap.contains(passwordResetToken)) {
            passwordResetTokenConcurrentHashMap.remove(passwordResetToken);
        }
    }

    public void save(PasswordResetToken prt) {
        passwordResetTokenConcurrentHashMap.put(prt.getToken(), prt);
    }
}