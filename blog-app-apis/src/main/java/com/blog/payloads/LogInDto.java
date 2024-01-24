package com.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LogInDto {
    private String userNameOrEmail;
    private String password;
}
