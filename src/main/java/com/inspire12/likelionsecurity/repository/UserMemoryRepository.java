package com.inspire12.likelionsecurity.repository;

import com.inspire12.likelionsecurity.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserMemoryRepository  {
    private final ConcurrentHashMap<String, UserEntity> usersHashMap = new ConcurrentHashMap<>();


    public boolean existsByUsername(String username) {
        return usersHashMap.contains(username);
    }

    public void save(UserEntity user) {
        usersHashMap.put(user.getUsername(), user);
    }

    public UserEntity get(String username) {
        return usersHashMap.get(username);
    }
}
