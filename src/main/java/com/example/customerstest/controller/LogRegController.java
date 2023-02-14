package com.example.customerstest.controller;

import com.example.customerstest.dto.UserDto;
import com.example.customerstest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogRegController {
    @Autowired
    UserService userService;

    @ModelAttribute("user")
    public UserDto userRegistrationDto() {
        return new UserDto();
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("registration")
    public String registerUserAccount(@ModelAttribute("user") UserDto userRegistrationDto) {
        userService.create(userRegistrationDto);
        return "redirect:/registration?success";
    }

}
