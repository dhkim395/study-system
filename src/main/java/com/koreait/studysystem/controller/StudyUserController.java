package com.koreait.studysystem.controller;

import com.koreait.studysystem.entity.StudyUser;
import com.koreait.studysystem.service.StudyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class StudyUserController {

    private final StudyUserService userService;

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute StudyUser user) {
        userService.register(user);
        return "redirect:/login";
    }
}