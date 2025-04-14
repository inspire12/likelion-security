package com.inspire12.likelionsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/security")
@RestController
public class ApiController {

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }
}
