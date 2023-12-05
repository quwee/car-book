package com.example.car_book.config;

import com.example.car_book.model.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("user")
    public User user() {
        return User.getCurrentUser().orElse(null);
    }

    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated() {
        System.out.println("isAuthenticated: " + User.isAuthenticated());
        return User.isAuthenticated();
    }

    @ModelAttribute("isAdmin")
    public Boolean isAdmin() {
        return User.isAdmin();
    }
}
