package com.inspire12.likelionsecurity.presentation.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private List<String> roles;

    public static UserResponse of(String username, List<String> roles) {
        return new UserResponse(username, roles);
    }
}
