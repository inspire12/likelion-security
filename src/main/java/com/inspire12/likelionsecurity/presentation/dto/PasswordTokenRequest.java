package com.inspire12.likelionsecurity.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordTokenRequest {
    private String token;
    private String newPassword;
} 