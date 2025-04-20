package com.inspire12.likelionsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ViewController {

    @GetMapping({"", "/"})
    public String home(Model model, Principal principal) {
        // 로그인한 사용자 이름 가져오기 (SecurityContextHolder 도 가능)
        model.addAttribute("username", principal.getName());
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
