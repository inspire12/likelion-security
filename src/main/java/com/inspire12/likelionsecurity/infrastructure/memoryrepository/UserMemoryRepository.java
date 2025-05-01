package com.inspire12.likelionsecurity.infrastructure.memoryrepository;

import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserMemoryRepository {

    private final ConcurrentHashMap<String, UserEntity> userDatasource = new ConcurrentHashMap<>();
    private final Logger log = LoggerFactory.getLogger(UserMemoryRepository.class);

    public UserMemoryRepository() {
    }

    public UserEntity findByUsername(String username) {
        if (!userDatasource.containsKey(username)) {
            log.info(username + " not found");
            return null;
        }
        return userDatasource.get(username);
    }

    // 사용자 저장 메서드 추가 (회원가입)
    public UserEntity save(UserEntity user) {
//        if (userDatasource.containsKey(user.getUsername())) {
//            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
//        }
        userDatasource.put(user.getUsername(), user);
        return user;
    }


    public boolean existsByUsername(String username) {
        return userDatasource.contains(username);
    }

    public UserEntity get(String username) {
        return userDatasource.get(username);
    }
}
