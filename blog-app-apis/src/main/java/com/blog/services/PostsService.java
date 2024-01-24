package com.blog.services;

import com.blog.payloads.PostsDto;

import java.util.List;

public interface PostsService {

    PostsDto createPost(PostsDto postsDto,Long userId,Long CategoryId);

    PostsDto updatePost(PostsDto postsDto,Long postId);

    PostsDto getPost(Long postId);

    List<PostsDto> getAllPostsList();

    void deletePost(Long postId);

    List<PostsDto> getPostByCategory(Long categoryId);

    List<PostsDto> getPostByUser(Long userId);

    List<PostsDto> searchPosts(String keyword);

}
