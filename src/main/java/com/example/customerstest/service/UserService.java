package com.example.customerstest.service;

import com.example.customerstest.dto.UserDto;
import com.example.customerstest.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findByLogin(String login);
    User create(UserDto user);
    boolean delete(long id);
    boolean update(User user, Long id);
}
