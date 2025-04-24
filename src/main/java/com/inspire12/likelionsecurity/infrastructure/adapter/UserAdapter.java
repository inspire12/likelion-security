package com.inspire12.likelionsecurity.infrastructure.adapter;

import com.inspire12.likelionsecurity.application.repository.UserRepository;
import com.inspire12.likelionsecurity.domain.User;
import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;
import com.inspire12.likelionsecurity.infrastructure.memoryrepository.UserMemoryRepository;
import com.inspire12.likelionsecurity.support.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAdapter implements UserRepository {

    private final PasswordEncoder passwordEncoder;
    private final UserMemoryRepository userMemoryRepository;

    public UserAdapter(PasswordEncoder passwordEncoder, UserMemoryRepository userMemoryRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userMemoryRepository = userMemoryRepository;
    }

    @Override
    public User signup(String username, String password, List<? extends GrantedAuthority> grantedAuthorities) {
        String encodedPassword = passwordEncoder.encode(password);
        UserEntity user = new UserEntity(username, encodedPassword, grantedAuthorities);
        UserEntity userSaved = userMemoryRepository.save(user);
        return UserMapper.fromEntity(userSaved);
    }
}
