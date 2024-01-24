package com.blog.services.impl;

import com.blog.entities.Comment;
import com.blog.entities.Posts;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostsRepository;
import com.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation class for managing comments on posts.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Creates a new comment for a specific post.
     *
     * @param commentDto The data for creating the comment.
     * @param postId     The ID of the post for which the comment is created.
     * @return A CommentDto representing the created comment.
     * @throws ResourceNotFoundException If the post with the given ID is not found.
     */
    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Posts postData = postsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "PostId", postId));
        Comment commentData = modelMapper.map(commentDto,Comment.class);
        commentData.setPost(postData);
        Comment saveComment = commentRepository.save(commentData);
        return modelMapper.map(saveComment,CommentDto.class);
    }

    /**
     * Deletes a comment based on its ID.
     *
     * @param commentId The ID of the comment to be deleted.
     * @throws ResourceNotFoundException If the comment with the given ID is not found.
     */
    @Override
    public void deleteComment(Long commentId) {
        Comment comment  = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","comment id",commentId));
        commentRepository.delete(comment);
    }
}
