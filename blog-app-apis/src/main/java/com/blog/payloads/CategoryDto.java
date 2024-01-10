package com.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {

    private Integer categoryId;

    @NotNull(message = "title is not null..!")
    @NotEmpty(message = "title is not empty")
    private String categoryTitle;

    @NotNull(message = "desc is not null..!")
    @NotEmpty(message = "desc is not empty..!")
    private String categoryDescription;

}
