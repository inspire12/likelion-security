package com.inspire12.likelionsecurity.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserEntity {

    private String username;
    private String email;
    private String password;
    private List<? extends GrantedAuthority> roles;

    public List<String> getRolesGranted() {
        return this.roles.stream().map(GrantedAuthority::getAuthority).toList();
    }

    public UserEntity(String email, String password, List<? extends GrantedAuthority> roles) {
        this.username = email;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public void changePassword(String encode, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(encode);
    }
}
