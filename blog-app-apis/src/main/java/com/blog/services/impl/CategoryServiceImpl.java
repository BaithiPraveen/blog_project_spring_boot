package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepository;
import com.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category_data = modelMapper.map(categoryDto,Category.class);
        Category save_data = categoryRepository.save(category_data);
        return modelMapper.map(save_data,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {

        Category category_data = categoryRepository.findById(id.intValue()).orElseThrow(()->new ResourceNotFoundException("Category","id",id));

        category_data.setCategoryTitle(categoryDto.getCategoryTitle());
        category_data.setCategoryDescription(categoryDto.getCategoryDescription());

        Category update_data = categoryRepository.save(category_data);
        return modelMapper.map(update_data,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category_data = categoryRepository.findById(id.intValue()).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
        categoryRepository.delete(category_data);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category_data = categoryRepository.findById(id.intValue()).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
        return modelMapper.map(category_data,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategoryList() {
        List<Category> category_list = categoryRepository.findAll();
        return category_list.stream().map(category_data -> modelMapper.map(category_data,CategoryDto.class)).collect(Collectors.toList());
    }
}
