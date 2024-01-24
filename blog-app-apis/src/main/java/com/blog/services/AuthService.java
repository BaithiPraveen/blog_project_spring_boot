package com.blog.services;

import com.blog.payloads.LogInDto;
import com.blog.payloads.RegisterDto;

public interface AuthService {

    String logIn(LogInDto logInDto);
    String register(RegisterDto registerDto);
}
