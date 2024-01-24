package com.blog.services.impl;

import com.blog.entities.User;
import com.blog.exceptions.EmailAlreadyExistsException;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.exceptions.UserNameAlreadyExistsException;
import com.blog.payloads.UserDto;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation class for managing user-related operations.
 * It performs the operations like create,get,getAll,update,delete on users.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * Creates a new user based on the provided UserDto.
     *
     * @param userDto The UserDto containing the data for the new user.
     * @return A UserDto representing the created user.
     * @throws UserNameAlreadyExistsException If the provided username already exists in the system.
     * @throws EmailAlreadyExistsException    If the provided email address already exists in the system.
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername()))
            throw new UserNameAlreadyExistsException(String.format("%s this userName is already existed", userDto.getUsername()));
        if (userRepository.existsByEmail(userDto.getEmail()))
            throw new EmailAlreadyExistsException(String.format("%s this email is already existed", userDto.getEmail()));

        User user = this.dtoTouser(userDto);
        User user_data = this.userRepository.save(user);
        return this.userToDto(user_data);
    }

    /**
     * Updates an existing user's information based on the provided UserDto and user ID.
     *
     * @param userDto The UserDto containing the updated data for the user.
     * @param id      The ID of the user to be updated.
     * @return A UserDto representing the updated user.
     * @throws ResourceNotFoundException If no user with the given ID is found.
     */
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

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user.
     * @return A UserDto representing the user.
     * @throws ResourceNotFoundException If no user with the given ID is found.
     */
    @Override
    public UserDto getUser(long id) {
        User user = this.userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        return this.userToDto(user);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return List of UserDto representing all users.
     */
    @Override
    public List<UserDto> getAllUsers() {
        List<User> user_list  = userRepository.findAll();
        return user_list.stream().map(this::userToDto).collect(Collectors.toList());
    }

    /**
     * Deletes a user based on the user ID.
     *
     * @param id The ID of the user to be deleted.
     * @throws ResourceNotFoundException If no user with the given ID is found.
     */
    @Override
    public void deleteUser(long id) {
        User user = this.userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        this.userRepository.delete(user);
    }

    /**
     * Retrieves a user by name.
     *
     * @param name The name of the user.
     * @return A UserDto representing the user.
     * @throws ResourceNotFoundException If no user with the given name is found.
     */
    @Override
    public UserDto findByName(String name) {
        User user = userRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("USER", "name", name));
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * Retrieves a user by name or email.
     *
     * @param username The name of the username.
     * @param email    The name of the email.
     * @return A UserDto representing the user.
     * @throws ResourceNotFoundException If no user with the given username or email is found.
     */
    @Override
    public UserDto findByUsernameOrEmail(String username, String email) {
        User user = userRepository.findByUsernameOrEmail(username, email).orElseThrow(() -> new ResourceNotFoundException("USER", "username or email", username + " or " + email));
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * Checks whether a user with the given email address exists in the user.
     *
     * @param email The email address to check for existence.
     * @return {@code true} if a user with the specified email address exists, {@code false} otherwise.
     */
    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    /**
     * Checks whether a user with the given username exists in the user.
     *
     * @param username The username address to check for existence.
     * @return {@code true} if a user with the specified username exists, {@code false} otherwise.
     */
    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Retrieves a user by username.
     *
     * @param username The name of the user.
     * @return A UserDto representing the user.
     * @throws ResourceNotFoundException If no user with the given username is found.
     */
    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("USER", "username", username));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("USER", "email", email));
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * Maps a UserDto to a User entity.
     *
     * @param userDto The UserDto to be mapped.
     * @return A User entity.
     */
    public User dtoTouser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    /**
     * Maps a User entity to a UserDto.
     *
     * @param user The User entity to be mapped.
     * @return A UserDto.
     */
    public UserDto userToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
