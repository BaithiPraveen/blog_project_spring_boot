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

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Posts postData = postsRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Posts","Postid",postId));
        Comment commentData = modelMapper.map(commentDto,Comment.class);
        commentData.setPost(postData);
        Comment saveComment = commentRepository.save(commentData);
        return modelMapper.map(saveComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment  = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","comment id",commentId));
        commentRepository.delete(comment);
    }
}
