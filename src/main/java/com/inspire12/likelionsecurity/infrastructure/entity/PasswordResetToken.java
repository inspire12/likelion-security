package com.inspire12.likelionsecurity.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    private String token;

    private String username;

    private LocalDateTime expiryDate;

    // 생성자, getter/setter 생략
}