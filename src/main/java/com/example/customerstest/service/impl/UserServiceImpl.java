package com.example.customerstest.service.impl;

import com.example.customerstest.dto.UserDto;
import com.example.customerstest.entity.Role;
import com.example.customerstest.entity.User;
import com.example.customerstest.exceptions.UserExistException;
import com.example.customerstest.exceptions.UserNotFoundException;
import com.example.customerstest.repository.UserRepository;
import com.example.customerstest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public User create(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.info("Ошибка при регистрации", e.getMessage());
            throw new UserExistException("этот пользователь " + user.getUsername() + " уже зарегестрирован");
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User c id " + id + " не наден"));
    }


    @Override
    public boolean delete(long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean update(User newUser) {
        User user = userRepository.findById(newUser.getId()).orElseThrow();
        newUser.setRoles(user.getRoles());
        userRepository.saveAndFlush(newUser);
        return true;
    }

}
