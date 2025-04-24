package com.inspire12.likelionsecurity.support;

import com.inspire12.likelionsecurity.domain.User;
import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;

public class UserMapper {
    public static User fromEntity(UserEntity userEntity) {
        return new User(userEntity.getUsername(), userEntity.getRoles());
    }
}
