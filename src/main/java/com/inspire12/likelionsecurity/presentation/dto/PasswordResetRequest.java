package com.inspire12.likelionsecurity.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {
    private String email;
    private String token;
    private String password;
    private String checkPassword;
}