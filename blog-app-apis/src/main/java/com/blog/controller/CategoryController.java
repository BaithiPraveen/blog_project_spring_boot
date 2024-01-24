package com.blog.controller;

import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing category-related operations.
 * It performs the operations like create,get,getAll,update,delete on category.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * Create a new category.
     *
     * @param categoryDto The category data.
     * @return ResponseEntity containing the created category data.
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
            CategoryDto category = categoryService.createCategory(categoryDto);
            return new ResponseEntity<>(category,HttpStatus.CREATED);
    }

    /**
     * Update a category by ID.
     *
     * @param categoryDto The updated category data.
     * @param id          The ID of the category to update.
     * @return ResponseEntity containing the updated category data.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("id") long id) {
        CategoryDto category = categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.ok(category);
    }

    /**
     * Get a category by ID.
     *
     * @param id The ID of the category to retrieve.
     * @return ResponseEntity containing the category data.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long id)
    {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    /**
     * Get a list of all categories.
     *
     * @return ResponseEntity containing a list of category data.
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategoryList()
    {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    /**
     * Delete a category by ID.
     *
     * @param id The ID of the category to delete.
     * @return ResponseEntity indicating the success of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id)
    {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(String.format("%s post category successfully..!", id));
    }

}
