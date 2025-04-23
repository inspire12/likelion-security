package com.inspire12.likelionsecurity.infrastructure.security;

import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final List<? extends GrantedAuthority> authorities;


    public CustomUserDetails(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.authorities = userEntity.getRoles();
    }

    public CustomUserDetails(String username, List<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = "";
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
