package com.example.customerstest.mapper;

import com.example.customerstest.dto.UserDto;
import com.example.customerstest.entity.User;
import com.example.customerstest.web.request.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    UserRequest userDtoToUserRequest(UserDto userDto);

    UserDto userRequestToUserDto(UserRequest userRequest);
}
