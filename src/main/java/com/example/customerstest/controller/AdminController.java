package com.example.customerstest.controller;

import com.example.customerstest.dto.UserDto;
import com.example.customerstest.entity.User;
import com.example.customerstest.mapper.UserMapper;
import com.example.customerstest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {


    private UserService userService;

    private UserMapper userMapper;

    public AdminController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> allUsers() {
      List<UserDto> list = userService.findAllUsers().stream()
              .map(userMapper :: userToUserDto)
              .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        UserDto userDto = userMapper.userToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
          userService.update(userMapper.userDtoToUser(userDto), id);
          return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }
    
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
     return new ResponseEntity<>(userMapper.userToUserDto(userService.create(userDto)), HttpStatus.OK);
    }

}
