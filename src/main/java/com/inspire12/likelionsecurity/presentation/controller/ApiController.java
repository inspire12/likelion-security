package com.inspire12.likelionsecurity.presentation.controller;

import com.inspire12.likelionsecurity.presentation.controller.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiController {

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> getMe(Principal principal) {
        String name = principal.getName();
        LoginResponse response = new LoginResponse("", name, List.of());
        return ResponseEntity.ok().body(response);
    }

}
