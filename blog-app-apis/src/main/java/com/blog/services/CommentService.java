package com.blog.services;

import com.blog.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Long PostId);

    void deleteComment(Long commentId);
}
