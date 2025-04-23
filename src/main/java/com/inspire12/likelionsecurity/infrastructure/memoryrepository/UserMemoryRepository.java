package com.inspire12.likelionsecurity.infrastructure.memoryrepository;

import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserMemoryRepository {

    private final ConcurrentHashMap<String, UserEntity> userDatasource = new ConcurrentHashMap<>();

    public UserMemoryRepository() {
    }

    public UserEntity findByUsername(String username) {
        if (!userDatasource.containsKey(username)) {
            return null;
        }
        return userDatasource.get(username);
    }

    // 사용자 저장 메서드 추가 (회원가입)
    public UserEntity save(UserEntity user) {
        if (userDatasource.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        userDatasource.put(user.getUsername(), user);
        return user;
    }
}
