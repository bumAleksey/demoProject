package com.example.customerstest.dto;

import lombok.Data;


@Data
public class UserDto {
    private String login;
    private String name;
    private String email;
    private String password;
}
