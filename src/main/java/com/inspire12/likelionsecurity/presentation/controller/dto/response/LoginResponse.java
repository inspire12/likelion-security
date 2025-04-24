package com.inspire12.likelionsecurity.presentation.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String username;
    private List<String> roles;

    public static LoginResponse failLoginResponse = new LoginResponse("인증 실패", "", List.of());
}