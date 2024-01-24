package com.blog.controller;


import com.blog.payloads.PostsDto;
import com.blog.services.PostsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing post-related operations.
 * It performs the operations like create,get,getAll,update,delete on post.
 */
@RestController
@RequestMapping("/post")
public class PostsController {

    @Autowired
    private PostsService postsService;


    /**
     * this api used to Create a new post.
     *
     * @param postsDto   The post data.
     * @param userID     The user ID.
     * @param categoryId The category ID.
     * @return ResponseEntity containing a created post data.
     */
    @PostMapping("/user/{userID}/category/{categoryId}")
    public ResponseEntity<PostsDto> createPost(@Valid @RequestBody PostsDto postsDto , @PathVariable("userID") Long userID, @PathVariable("categoryId") Long categoryId)
    {
        PostsDto postDtoData = postsService.createPost(postsDto, userID, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDtoData);
    }

    /**
     * Get a list of posts based on the user ID.
     *
     * @param userID The user ID.
     * @return ResponseEntity containing a list of post data.
     */
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<PostsDto>> getPostListByUser(@PathVariable("userID") Long userID)
    {
        return ResponseEntity.ok(postsService.getPostByUser(userID));
    }

    /**
     * Get a list of posts based on the category ID.
     *
     * @param categoryID The category ID.
     * @return ResponseEntity containing a list of post data.
     */
    @GetMapping("/category/{categoryID}")
    public ResponseEntity<List<PostsDto>> getPostListByCategory(@PathVariable("categoryID") Long categoryID)
    {
        return ResponseEntity.ok(postsService.getPostByCategory(categoryID));
    }

    /**
     * Get a list of all posts.
     *
     * @return ResponseEntity containing a list of all post data.
     */
    @GetMapping
    public ResponseEntity<List<PostsDto>> getAllPostList()
    {
        return ResponseEntity.ok(postsService.getAllPostsList());
    }

    /**
     * Retrieve a specific post by ID.
     *
     * @param postId The ID of the post to retrieve.
     * @return ResponseEntity containing the post data.
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostsDto> getPost(@PathVariable("postId")Long postId)
    {
        return ResponseEntity.ok(postsService.getPost(postId));
    }

    /**
     * Update a post by ID (requires ADMIN role).
     *
     * @param postsDto The updated post data.
     * @param postId   The ID of the post to update.
     * @return ResponseEntity containing the updated post data.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{postId}")
    public ResponseEntity<PostsDto> updatePost(@Valid @RequestBody PostsDto postsDto, @PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postsService.updatePost(postsDto, postId));
    }

    /**
     * Delete a post by ID (requires ADMIN role).
     *
     * @param postId The ID of the post to delete.
     * @return ResponseEntity indicating the success of the deletion.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId)
    {
        postsService.deletePost(postId);
        return ResponseEntity.ok(String.format("%s post deleted successfully..!", postId));
    }

    /**
     * Retrieve a list of posts by title.
     *
     * @param titleName The title to search for.
     * @return ResponseEntity containing a list of post data.
     */
    @GetMapping("/title/{titleName}")
    public ResponseEntity<List<PostsDto>> getPostListByTitleName(@PathVariable("titleName") String titleName)
    {
        return ResponseEntity.ok(postsService.searchPosts(titleName));
    }

}
