package com.filmbook.controllers.comment;

import com.filmbook.model.database.Comment;
import com.filmbook.model.dto.CommentDto;
import com.filmbook.services.commentOperations.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/getCommentsByReviewId/{reviewId}")
    private List<CommentDto> getCommentsByReviewId(@PathVariable String reviewId){
        return commentService.getCommentsByReviewId(reviewId);
    }
    @PostMapping("/addComment/{reviewId}/{userId}")
    private void addComment(@PathVariable String reviewId, @PathVariable String userId, @RequestBody String content){
        commentService.addComment(Long.valueOf(reviewId),Long.valueOf(userId),content);
    }
    @DeleteMapping("/deleteComment/{commentId}")
    private void deleteComment(@PathVariable String commentId){
        commentService.deleteComment(Long.valueOf(commentId));
    }
}
