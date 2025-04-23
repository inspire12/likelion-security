package com.inspire12.likelionsecurity.infrastructure.adapter;

import com.inspire12.likelionsecurity.application.port.in.UserRepository;
import com.inspire12.likelionsecurity.domain.User;
import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;
import com.inspire12.likelionsecurity.infrastructure.memoryrepository.UserMemoryRepository;
import com.inspire12.likelionsecurity.support.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAdapter implements UserRepository {
    private final UserMemoryRepository userMemoryRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAdapter(UserMemoryRepository userMemoryRepository, PasswordEncoder passwordEncoder) {
        this.userMemoryRepository = userMemoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String email) {
        return UserMapper.fromEntity(userMemoryRepository.findByUsername(email));
    }

    @Override
    public void changePassword(String usernameByToken, String newPassword) {
        UserEntity userEntity = userMemoryRepository.findByUsername(usernameByToken);
        userEntity.changePassword(newPassword, passwordEncoder);
    }
}
