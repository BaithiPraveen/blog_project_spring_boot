package com.blog.controller;

import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing comment-related operations.
 * It performs the operations like create,delete on comment.
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Create a comment for a specific post.
     *
     * @param commentDto The comment data.
     * @param postId     The ID of the post to which the comment belongs.
     * @return ResponseEntity containing the created comment data.
     */
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable("postId") Long postId)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentDto, postId));
    }

    /**
     * Delete a comment by ID.
     *
     * @param commentId The ID of the comment to delete.
     * @return ResponseEntity indicating the success of the deletion.
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId)
    {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(String.format(" this %s comment is deleted successfully", commentId));
    }
}
