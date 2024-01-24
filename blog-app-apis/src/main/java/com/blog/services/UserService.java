package com.blog.services;

import com.blog.payloads.UserDto;

import java.util.List;

public interface UserService{

    public UserDto createUser(UserDto user);

    UserDto updateUser(UserDto userDto,long id);

    UserDto getUser(long id);

    List<UserDto> getAllUsers();

    void deleteUser(long id);

    public Boolean existsByEmail(String email);

    public Boolean existsByUsername(String username);

    public UserDto findByName(String name);

    public UserDto findByUsernameOrEmail(String username, String email);

    public UserDto findByUsername(String username);

    public UserDto findByEmail(String email);


}
