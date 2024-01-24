package com.blog.services.impl;

import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.CustomExceptionHandler;
import com.blog.exceptions.EmailAlreadyExistsException;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.exceptions.UserNameAlreadyExistsException;
import com.blog.payloads.LogInDto;
import com.blog.payloads.RegisterDto;
import com.blog.repositories.RoleRepository;
import com.blog.repositories.UserRepository;
import com.blog.security.JwtAuthenticationProvider;
import com.blog.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * Service implementation class for managing authentication operations.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtAuthenticationProvider authenticationProvider;

    /**
     * Authenticates a user based on login credentials.
     *
     * @param logInDto The login credentials.
     * @return A message indicating successful login.
     */
    @Override
    public String logIn(LogInDto logInDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logInDto.getUserNameOrEmail(), logInDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return authenticationProvider.generateJwtToken(authenticate);
    }

    /**
     * Registers a new user.
     *
     * @param registerDto The user registration data.
     * @return A message indicating successful registration.
     * @throws UserNameAlreadyExistsException If the username already exists.
     * @throws EmailAlreadyExistsException    If the email already exists.
     */
    @Override

    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername()) && userRepository.existsByEmail(registerDto.getEmail()))
            throw new CustomExceptionHandler("UserName and Email already existed in users.");
        if (userRepository.existsByUsername(registerDto.getUsername()))
            throw new UserNameAlreadyExistsException(String.format("%s this userName is already existed",registerDto.getUsername()));
        if (userRepository.existsByEmail(registerDto.getEmail()))
            throw new EmailAlreadyExistsException(String.format("%s this email is already existed",registerDto.getEmail()));

        User user = modelMapper.map(registerDto,User.class);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role>roles = new HashSet<>();
        System.out.println("role data : "+roleRepository.findByName("ROLE_USER"));
        Role roleData = roleRepository.findByName("ROLE_USER").orElseThrow(()->new ResourceNotFoundException("Role","roleName","ROLE_USER"));
        roles.add(roleData);
        user.setRole(roles);
        user.setAbout("i am "+registerDto.getName());
        User saveUser = userRepository.save(user);
        return String.format("%s user is created.",user.getUsername());
    }
}
