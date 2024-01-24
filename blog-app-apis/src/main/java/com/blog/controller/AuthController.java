package com.blog.controller;

import com.blog.payloads.JWTAuthResponse;
import com.blog.payloads.LogInDto;
import com.blog.payloads.RegisterDto;
import com.blog.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controller class for managing Authentication_user-related operations.
 * It performs the operations like login and register on Authentication_user.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    /**
     * Perform user login.
     *
     * @param logInDto The login data.
     * @return ResponseEntity containing the result of the login operation.
     */
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> logIn(@RequestBody LogInDto logInDto){
        String token = authService.logIn(logInDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    /**
     * Register a new user.
     *
     * @param registerDto The registration data.
     * @return ResponseEntity containing the result of the registration operation.
     */
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String result = authService.register(registerDto);
        return ResponseEntity.ok(result);
    }
}
