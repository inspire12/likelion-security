package com.inspire12.likelionsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;

    private List<? extends GrantedAuthority> roles;

    public List<String> getRolesGranted() {
        return this.roles.stream().map(GrantedAuthority::getAuthority).toList();
    }

}
