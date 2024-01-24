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

/**
 * Service implementation class for managing categories.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * Creates a new category.
     *
     * @param categoryDto The data for creating the category.
     * @return A CategoryDto representing the created category.
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category_data = modelMapper.map(categoryDto,Category.class);
        Category save_data = categoryRepository.save(category_data);
        return modelMapper.map(save_data,CategoryDto.class);
    }

    /**
     * Updates an existing category based on its ID.
     *
     * @param categoryDto The updated data for the category.
     * @param id          The ID of the category to be updated.
     * @return A CategoryDto representing the updated category.
     * @throws ResourceNotFoundException If the category with the given ID is not found.
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {

        Category category_data = categoryRepository.findById(id.intValue()).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));

        category_data.setCategoryTitle(categoryDto.getCategoryTitle());
        category_data.setCategoryDescription(categoryDto.getCategoryDescription());

        Category update_data = categoryRepository.save(category_data);
        return modelMapper.map(update_data,CategoryDto.class);
    }

    /**
     * Deletes a category based on its ID.
     *
     * @param id The ID of the category to be deleted.
     * @throws ResourceNotFoundException If the category with the given ID is not found.
     */
    @Override
    public void deleteCategory(Long id) {
        Category category_data = categoryRepository.findById(id.intValue()).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
        categoryRepository.delete(category_data);
    }

    /**
     * Retrieves a category based on its ID.
     *
     * @param id The ID of the category to be retrieved.
     * @return A CategoryDto representing the retrieved category.
     * @throws ResourceNotFoundException If the category with the given ID is not found.
     */
    @Override
    public CategoryDto getCategory(Long id) {
        Category category_data = categoryRepository.findById(id.intValue()).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
        return modelMapper.map(category_data,CategoryDto.class);
    }

    /**
     * Retrieves a list of all categories.
     *
     * @return A list of CategoryDto representing all categories.
     */
    @Override
    public List<CategoryDto> getCategoryList() {
        List<Category> category_list = categoryRepository.findAll();
        return category_list.stream().map(category_data -> modelMapper.map(category_data,CategoryDto.class)).collect(Collectors.toList());
    }
}
