package com.blog.controller;

import com.blog.payloads.PostsDto;
import com.blog.services.PostsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @PostMapping("/user/{userID}/category/{categoryId}")
    public ResponseEntity<PostsDto> createPost(@Valid @RequestBody PostsDto postsDto , @PathVariable("userID") Long userID, @PathVariable("categoryId") Long categoryId)
    {
        PostsDto postDtoData = postsService.createPost(postsDto, userID, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDtoData);
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<List<PostsDto>> getPostListByUser(@PathVariable("userID") Long userID)
    {
        return ResponseEntity.ok(postsService.getPostByUser(userID));
    }

    @GetMapping("/category/{categoryID}")
    public ResponseEntity<List<PostsDto>> getPostListByCategory(@PathVariable("categoryID") Long categoryID)
    {
        return ResponseEntity.ok(postsService.getPostByCategory(categoryID));
    }

    @GetMapping
    public ResponseEntity<List<PostsDto>> getAllPostList()
    {
        return ResponseEntity.ok(postsService.getAllPostsList());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostsDto> getPost(@PathVariable("postId")Long postId)
    {
        return ResponseEntity.ok(postsService.getPost(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity <PostsDto> updatePost(@Valid @RequestBody PostsDto postsDto,@PathVariable("postId") Long postId)
    {
        return ResponseEntity.ok(postsService.updatePost(postsDto,postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId)
    {
        postsService.deletePost(postId);
        return ResponseEntity.ok(String.format("%s post deleted successfully..!",postId));
    }

    @GetMapping("/title/{titleName}")
    public ResponseEntity<List<PostsDto>> getPostListByTitleName(@PathVariable("titleName") String titleName)
    {
        return ResponseEntity.ok(postsService.searchPosts(titleName));
    }

}
