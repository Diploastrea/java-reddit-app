package com.greenfoxacademy.reddit.controller;

import com.greenfoxacademy.reddit.model.User;
import com.greenfoxacademy.reddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model, @ModelAttribute User user) {
        model.addAttribute("usernameAvailable", true);
        model.addAttribute("passwordMatch", true);
        return "register";
    }

    @PostMapping("/register")
    public String addUser(Model model, @ModelAttribute User user) {
        Boolean registrationSuccessful = this.userService.validateUsername(user);
        Boolean passwordMatch = this.userService.validatePassword(user);
        if (registrationSuccessful && passwordMatch) this.userService.addUser(user);
        model.addAttribute("usernameAvailable", registrationSuccessful);
        model.addAttribute("passwordMatch", passwordMatch);
        return registrationSuccessful && passwordMatch ? "loginpromt" : "register";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model, @ModelAttribute User user) {
        model.addAttribute("loginSuccessful", true);
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(Model model, @ModelAttribute User user) {
        Boolean loginSuccessful = this.userService.validateUser(user);
        model.addAttribute("loginSuccessful", loginSuccessful);
        return loginSuccessful ? "redirect:/page/1?username=" + user.getUsername() : "login";
    }

    @GetMapping("/logout")
    public String logoutUser() {
        this.userService.logout();
        return "redirect:/login";
    }
}
