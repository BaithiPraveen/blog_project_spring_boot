package com.blog.payloads;

import com.blog.entities.Posts;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {

    private Long id;

    @NotNull(message = "content field is required..!")
    @NotEmpty(message = "content length more then the one character..!")
    private String content;


}
