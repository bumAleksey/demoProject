package com.example.customerstest.web.request;

import lombok.Data;

@Data
public class UserRequest {
    private String login;
    private String email;
    private String name;
    private String password;
}
