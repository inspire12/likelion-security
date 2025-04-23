package com.inspire12.likelionsecurity.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {
    private String username;
    private String message;
    private List<String> roles;

    public static final SignupResponse failSignupResponse = new SignupResponse("가입 실패", "가입 실패", List.of());
}
