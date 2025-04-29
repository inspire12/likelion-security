
package com.inspire12.likelionsecurity.presentation.controller;

import com.inspire12.likelionsecurity.application.service.AuthenticationService;
import com.inspire12.likelionsecurity.presentation.controller.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/status")
@RestController
public class ApiStatusController {

    private final AuthenticationService authenticationService;

    public ApiStatusController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/500")
    public ResponseEntity<UserResponse> api() {

        return ResponseEntity.internalServerError().build();
    }
    @GetMapping("/204")
    public ResponseEntity<UserResponse> get403() {

        return ResponseEntity.noContent().build();
    }


}
