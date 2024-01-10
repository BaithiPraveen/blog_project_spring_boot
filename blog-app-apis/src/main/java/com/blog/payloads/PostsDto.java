package com.blog.payloads;

import com.blog.entities.Category;
import com.blog.entities.Comment;
import com.blog.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PostsDto {
    private Long id;

    @NotNull(message = "title field is required..!")
    @NotEmpty(message = "title length more then the one character..!")
    private String title;

    @NotNull(message = "content field is required..!")
    @NotEmpty(message = "content length more then the one character..!")
    private String content;

    private String imageName;

    private Date addDate;

    private CategoryDto category;
    private UserDto users;
    private List<CommentDto> comment=new ArrayList<>();
}
