package com.blog.services;

import com.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService
{
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto,Long id);

    void deleteCategory(Long id);

    CategoryDto getCategory(Long id);

    List<CategoryDto> getCategoryList();
}
