package com.example.car_book.controller;

import com.example.car_book.model.User;
import com.example.car_book.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        if(User.isAuthenticated()) {
            return "redirect:/";
        }
        else {
            return "auth/login";
        }
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        if(User.isAuthenticated()) {
            return "redirect:/";
        }
        else {
            return "auth/registration";
        }
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("uploadUser") @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "auth/registration";

        userService.register(user);
        return "redirect:/auth/login";
    }
}
