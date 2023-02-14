package com.example.customerstest.controller;

import com.example.customerstest.dto.UserDto;
import com.example.customerstest.entity.User;
import com.example.customerstest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        return "index";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.delete(Long.parseLong(id));
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/index";
    }
}
