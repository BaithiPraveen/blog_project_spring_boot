package com.blog.services;

import com.blog.entities.User;
import com.blog.payloads.UserDto;

import java.util.List;

public interface UserService{

    public UserDto createUser(UserDto user);

    UserDto updateUser(UserDto userDto,long id);

    UserDto getUser(long id);

    List<UserDto> getAllUsers();

    void deleteUser(long id);
}
