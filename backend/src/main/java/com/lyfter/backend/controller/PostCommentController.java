package com.lyfter.backend.controller;

import com.lyfter.backend.payload.request.PostCommentRequest;
import com.lyfter.backend.payload.response.MessageResponse;
import com.lyfter.backend.service.PostCommentCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/post-comment")
public class PostCommentController {

    private final PostCommentCRUDService postCommentCRUDService;

    @Autowired
    public PostCommentController(PostCommentCRUDService postCommentCRUDService) {
        this.postCommentCRUDService = postCommentCRUDService;
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCommentsByPostId(@RequestParam int postId) {
        try {
            return ResponseEntity.ok(postCommentCRUDService.getByPostId(postId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addComment(@RequestBody PostCommentRequest postCommentRequest) {
        try {
            postCommentCRUDService.createPostComment(postCommentRequest);
            return ResponseEntity.ok(new MessageResponse("Post comment created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteComment(@RequestParam int commentId) {
        try {
            postCommentCRUDService.deletePostComment(commentId);
            return ResponseEntity.ok(new MessageResponse("Post comment deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
