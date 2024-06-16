package com.example.generatorsdiplomawork.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthController {
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

//    @GetMapping("/enc")
//    public String encPage() {;
//        String admin = new BCryptPasswordEncoder(5).encode("user");
//        return admin;
//    }
}
