package com.blog.controller;

import java.util.List;
import java.util.Map;

import com.blog.payloads.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.UserDto;
import com.blog.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("id") Long id)
    {
    	UserDto updated_user = userService.updateUser(userDto, id);
    	return ResponseEntity.ok(updated_user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id)
    {
    	userService.deleteUser(id);
    	return ResponseEntity.ok(new ApiResponse("user",true));
    }
    
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
    	List<UserDto> user_list = userService.getAllUsers();
    	return ResponseEntity.ok(user_list);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id)
    {
    	UserDto UserDto_data = userService.getUser(id);
    	return ResponseEntity.ok(UserDto_data);
    }
    
}
