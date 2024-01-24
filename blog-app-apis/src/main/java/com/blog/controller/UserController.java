package com.blog.controller;

import com.blog.payloads.UserDto;
import com.blog.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing user-related operations.
 * It performs the operations like create,get,getAll,update,delete on users.
 */
@Tag(
        name = "CRUD operations for users",
        description = "it performs the operations like create,get,getAll,update,delete on users."
)

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Create a user.
     *
     * @param userDto The user data.
     * @return ResponseEntity containing the created user data.
     */
    @Operation(
            summary = "create user Rest Api",
            description = "create the user based on the input data"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 created."
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {

        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    /**
     * Update the User.
     *
     * @param userDto The user.
     * @param id      The user id.
     * @return ResponseEntity containing the Updated user data.
     */
    @Operation(
            summary = "UPDATE user by ID Rest Api",
            description = "update the user data based on input id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success."
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("id") Long id) {
        UserDto updated_user = userService.updateUser(userDto, id);
        return ResponseEntity.ok(updated_user);

    }

    /**
     * Delete the user based user id.
     *
     * @param id The user id.
     * @return ResponseEntity indicating the success of the deletion.
     */
    @Operation(
            summary = "DELETE user by ID Rest Api",
            description = "DELETE the user data based on input id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(String.format("%s post deleted successfully..!", id));

    }

    /**
     * Retrieves a user by username or email.
     *
     * @param username The username to search the user.
     * @param email    The email to search the user.
     * @return ResponseEntity containing the UserDto if found.
     */
    @Operation(
            summary = "GET user by username or email Rest Api",
            description = "get the user data based on input username or email"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success.."
    )
    @GetMapping("/usernameoremail/")
    public ResponseEntity<UserDto> getUserByUsernameOrEmail(@RequestParam("username") String username, @RequestParam("email") String email) {
        return ResponseEntity.ok(userService.findByUsernameOrEmail(username, email));
    }

    /**
     * Retrieves a list of all users.
     *
     * @return ResponseEntity containing a list of UserDto.
     */
    @Operation(
            summary = "GET user list  Rest Api",
            description = "get all the users"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success.."
    )
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> user_list = userService.getAllUsers();
        return ResponseEntity.ok(user_list);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user.
     * @return ResponseEntity containing the UserDto if found.
     */
    @Operation(
            summary = "GET user by ID Rest Api",
            description = "get the user data based on input id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success.."
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        UserDto UserDto_data = userService.getUser(id);
        return ResponseEntity.ok(UserDto_data);
    }

    /**
     * Retrieves a user by name.
     *
     * @param name The name of the user.
     * @return ResponseEntity containing the UserDto if found.
     */
    @Operation(
            summary = "GET user by name Rest Api",
            description = "get the user data based on input name"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success.."
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }

    /**
     * Retrieves a user by username.
     *
     * @param username The username of the user.
     * @return ResponseEntity containing the UserDto if found.
     */
    @Operation(
            summary = "GET user by username Rest Api",
            description = "get the user data based on input username"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success.."
    )
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    /**
     * Retrieves a user by email.
     *
     * @param email The email of the user.
     * @return ResponseEntity containing the UserDto if found.
     */
    @Operation(
            summary = "GET user by email Rest Api",
            description = "get the user data based on input email"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success.."
    )
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }


}
