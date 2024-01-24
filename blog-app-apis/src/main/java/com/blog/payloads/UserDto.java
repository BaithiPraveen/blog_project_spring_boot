package com.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;

    @NotNull(message = "name field is required..!")
    @NotEmpty(message = "name length more then the one character..!")
    private String name;

    @NotNull(message = "username field is required..!")
    @NotEmpty(message = "username length more then the one character..!")
    private String username;

    @Email(message = "required valid email..!")
    @NotEmpty(message = "email is not empty..!" )
    private String email;

    @NotNull(message = "password field is not null..!")
    @Size(min =3,max = 10,message = "password must be 3 to 10 characters...!")
    private String password;

    @NotNull(message = "about field ids not null..!")
    @Size(min = 3 ,max = 50, message = "about is more than 3 chr and less than 50 char")
    private String about;
}
