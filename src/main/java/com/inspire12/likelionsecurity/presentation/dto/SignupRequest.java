package com.inspire12.likelionsecurity.presentation.dto;

import com.inspire12.likelionsecurity.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private List<String> roles = List.of(RoleEnum.ROLE_UESR.name());

    public static SignupRequest empty() {
        return new SignupRequest("", "", new ArrayList<>());
    }
}
