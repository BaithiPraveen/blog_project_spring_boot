package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.entities.Posts;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostsDto;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.PostsRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.PostsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostsDto createPost(PostsDto postsDto,Long userId,Long categoryId) {

        User userData = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        Category categoryData = categoryRepository.findById(categoryId.intValue()).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));

        Posts postsData = modelMapper.map(postsDto,Posts.class);
        postsData.setImageName("default.png");
        postsData.setUsers(userData);
        postsData.setCategory(categoryData);

        Posts savePost = postsRepository.save(postsData);
        return modelMapper.map(savePost,PostsDto.class);
    }

    @Override
    public PostsDto updatePost(PostsDto postsDto, Long postId) {
        Posts postData = postsRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Posts","Postid",postId));
        postData.setTitle(postsDto.getTitle());
        postData.setContent(postsDto.getContent());
        postData.setImageName(postsDto.getImageName());

        Posts updatedPost = postsRepository.save(postData);
        return modelMapper.map(updatedPost,PostsDto.class);
    }

    @Override
    public PostsDto getPost(Long postId) {
        Posts postData = postsRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Posts","PostId",postId));
        System.out.println("i am in service : "+postData.getComment());
        return modelMapper.map(postData,PostsDto.class);
    }

    @Override
    public List<PostsDto> getAllPostsList() {
        List<Posts> postsList = postsRepository.findAll();
        return postsList.stream().map(post -> modelMapper.map(post, PostsDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long postId) {
        Posts postData = postsRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Posts","PostId",postId));
        postsRepository.delete(postData);
    }

    @Override
    public List<PostsDto> getPostByCategory(Long categoryId) {
        Category categoryData = categoryRepository.findById(categoryId.intValue()).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        List<Posts> postsList = postsRepository.findByCategory(categoryData);
        return postsList.stream().map(post -> modelMapper.map(post, PostsDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostsDto> getPostByUser(Long userId) {
        User userData = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        List<Posts> postsList = postsRepository.findByUsers(userData);
        return postsList.stream().map(post -> modelMapper.map(post, PostsDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostsDto> searchPosts(String keyword) {
        List<Posts> postsList = postsRepository.findByTitleContainingIgnoreCase(keyword);
        return postsList.stream().map(post -> modelMapper.map(post, PostsDto.class)).collect(Collectors.toList());
    }
}
