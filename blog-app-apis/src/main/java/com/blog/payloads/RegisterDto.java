package com.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterDto {
    private String name;
    private String username;
    private String email;
    private String password;

}
