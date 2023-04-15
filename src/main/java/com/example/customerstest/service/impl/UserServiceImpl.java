package com.example.customerstest.service.impl;

import com.example.customerstest.dto.UserDto;
import com.example.customerstest.entity.Role;
import com.example.customerstest.entity.User;
import com.example.customerstest.exceptions.UserExistException;
import com.example.customerstest.exceptions.UserNotFoundException;
import com.example.customerstest.repository.UserRepository;
import com.example.customerstest.service.CityService;
import com.example.customerstest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {



    BCryptPasswordEncoder bCryptPasswordEncoder;


    private final UserRepository userRepository;

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
    public User findByLogin(String login) {
        User user = userRepository.findUserByLogin(login);
        return user;
    }
    @Override
    public User findById(Long id) {
       return userRepository.findById(id).get();
    }


    @Override
   @Transactional
    public boolean delete(long id) {
        userRepository.deleteById(id);
        ///
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

    //генерация данныйх юзеров при запуске;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setLogin("Valmont");
        user.setPassword(bCryptPasswordEncoder.encode("12345"));
        user.setEmail("valmont@mail.ru");
        user.setName("Vlad");
        user.setRoles(Collections.singleton(new Role("ADMIN")));
        userRepository.save(user);

        User user1 = new User();
        user1.setLogin("Anliks");
        user1.setPassword(bCryptPasswordEncoder.encode("12345"));
        user1.setEmail("anliks@mail.ru");
        user1.setName("Max");
        user1.setRoles(Collections.singleton(new Role("USER")));
        userRepository.save(user1);

    }
}
