package com.blog.services.impl;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoTouser(userDto);
        User user_data = this.userRepository.save(user);
        return this.userToDto(user_data);
    }

    @Override
    public UserDto updateUser(UserDto userDto, long id) {
        User user = this.userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User user_data = this.userRepository.save(user);
        return this.userToDto(user_data);
    }

    @Override
    public UserDto getUser(long id) {
        User user = this.userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> user_list  = this.userRepository.findAll();
        return user_list.stream().map(this::userToDto).collect(Collectors.toList());
//        return user_list.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(long id) {
        User user = this.userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        this.userRepository.delete(user);
    }
    public User dtoTouser(UserDto userDto)
    {
        User user = modelMapper.map(userDto,User.class);

//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }

    public UserDto userToDto(User user)
    {
        UserDto userDto = modelMapper.map(user,UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
