package com.example.customerstest.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserControllerAdvice {
    @ModelAttribute("login")
    public String addLoginToModel() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
