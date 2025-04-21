package com.inspire12.likelionsecurity.presentation.controller;

import com.inspire12.likelionsecurity.presentation.controller.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiController {

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> getMe() {
        return ResponseEntity.ok().build();
    }

}
