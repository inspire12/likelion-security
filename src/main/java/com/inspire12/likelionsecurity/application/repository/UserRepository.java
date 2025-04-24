package com.inspire12.likelionsecurity.application.repository;

import com.inspire12.likelionsecurity.domain.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface UserRepository {

    User signup(String username, String password, List<? extends GrantedAuthority> grantedAuthorities);
}
