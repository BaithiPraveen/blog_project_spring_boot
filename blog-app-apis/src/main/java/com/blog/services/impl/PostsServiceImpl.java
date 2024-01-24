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

/**
 * Service implementation class for managing post-related operations.
 */
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

    /**
     * Creates a new post with the provided data.
     *
     * @param postsDto   The data for creating the post.
     * @param userId     The ID of the user associated with the post.
     * @param categoryId The ID of the category associated with the post.
     * @return A PostsDto representing the created post.
     * @throws ResourceNotFoundException If the associated user or category is not found.
     */
    @Override
    public PostsDto createPost(PostsDto postsDto,Long userId,Long categoryId) {

        User userData = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Category categoryData = categoryRepository.findById(categoryId.intValue()).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));

        Posts postsData = modelMapper.map(postsDto,Posts.class);
        postsData.setImageName("default.png");
        postsData.setUsers(userData);
        postsData.setCategory(categoryData);

        Posts savePost = postsRepository.save(postsData);
        return modelMapper.map(savePost,PostsDto.class);
    }

    /**
     * Updates an existing post with the provided data.
     *
     * @param postsDto The updated data for the post.
     * @param postId   The ID of the post to be updated.
     * @return A PostsDto representing the updated post.
     * @throws ResourceNotFoundException If the post with the given ID is not found.
     */
    @Override
    public PostsDto updatePost(PostsDto postsDto, Long postId) {
        Posts postData = postsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "PostId", postId));
        postData.setTitle(postsDto.getTitle());
        postData.setContent(postsDto.getContent());
        postData.setImageName(postsDto.getImageName());

        Posts updatedPost = postsRepository.save(postData);
        return modelMapper.map(updatedPost,PostsDto.class);
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId The ID of the post.
     * @return A PostsDto representing the post.
     * @throws ResourceNotFoundException If the post with the given ID is not found.
     */
    @Override
    public PostsDto getPost(Long postId) {
        Posts postData = postsRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Posts","PostId",postId));
        return modelMapper.map(postData,PostsDto.class);
    }

    /**
     * Retrieves a list of all posts.
     *
     * @return List of PostsDto representing all posts.
     */
    @Override
    public List<PostsDto> getAllPostsList() {
        List<Posts> postsList = postsRepository.findAll();
        return postsList.stream().map(post -> modelMapper.map(post, PostsDto.class)).collect(Collectors.toList());
    }

    /**
     * Deletes a post based on its ID.
     *
     * @param postId The ID of the post to be deleted.
     * @throws ResourceNotFoundException If the post with the given ID is not found.
     */
    @Override
    public void deletePost(Long postId) {
        Posts postData = postsRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Posts","PostId",postId));
        postsRepository.delete(postData);
    }

    /**
     * Retrieves a list of posts associated with a specific category.
     *
     * @param categoryId The ID of the category.
     * @return List of PostsDto representing posts in the specified category.
     * @throws ResourceNotFoundException If the category with the given ID is not found.
     */
    @Override
    public List<PostsDto> getPostByCategory(Long categoryId) {
        Category categoryData = categoryRepository.findById(categoryId.intValue()).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        List<Posts> postsList = postsRepository.findByCategory(categoryData);
        return postsList.stream().map(post -> modelMapper.map(post, PostsDto.class)).collect(Collectors.toList());
    }

    /**
     * Retrieves a list of posts associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return List of PostsDto representing posts by the specified user.
     * @throws ResourceNotFoundException If the user with the given ID is not found.
     */
    @Override
    public List<PostsDto> getPostByUser(Long userId) {
        User userData = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Posts> postsList = postsRepository.findByUsers(userData);
        return postsList.stream().map(post -> modelMapper.map(post, PostsDto.class)).collect(Collectors.toList());
    }

    /**
     * Searches for posts containing a specified keyword in their titles.
     *
     * @param keyword The keyword to search for in post titles.
     * @return List of PostsDto representing posts matching the search criteria.
     */
    @Override
    public List<PostsDto> searchPosts(String keyword) {
        List<Posts> postsList = postsRepository.findByTitleContainingIgnoreCase(keyword);
        return postsList.stream().map(post -> modelMapper.map(post, PostsDto.class)).collect(Collectors.toList());
    }
}
