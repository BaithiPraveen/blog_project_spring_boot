package com.blog.utils;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserPasswordEncoder {
    public static void main(String [] args)
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("anu"));
        System.out.println(passwordEncoder.encode("raki"));
        System.out.println(passwordEncoder.encode("sneha"));

    }

}
