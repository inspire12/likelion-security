package com.inspire12.likelionsecurity.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private String username;
    private String password;
    private List<? extends GrantedAuthority> roles;

    public List<String> getRolesGranted() {
        return this.roles.stream().map(GrantedAuthority::getAuthority).toList();
    }
}
