package com.inspire12.likelionsecurity.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
    private List<String> roles;

    public static LoginResponse failLoginResponse = new LoginResponse("인증 실패", "", List.of());
}