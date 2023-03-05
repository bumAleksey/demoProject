package com.example.customerstest.service.impl;

import com.example.customerstest.dto.UserDto;
import com.example.customerstest.entity.Role;
import com.example.customerstest.entity.User;
import com.example.customerstest.exceptions.UserExistException;
import com.example.customerstest.exceptions.UserNotFoundException;
import com.example.customerstest.repository.UserRepository;
import com.example.customerstest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public User create(UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRoles(Collections.singleton(new Role("USER")));
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.info("Ошибка при регистрации", e.getMessage());
            throw new UserExistException("этот пользователь " + user.getLogin() + " уже зарегестрирован");
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
    @Transactional
    public boolean update(User newUser, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("обновлять нечего"));
        newUser.setId(id);
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(user.getRoles());
        userRepository.save(newUser);
        return true;
    }

}
