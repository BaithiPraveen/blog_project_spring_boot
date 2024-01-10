package com.blog.controller;

import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable("postId") Long postId)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentDto,postId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId)
    {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(String.format(" this %s comment is deleted successfully",commentId));
    }
}
