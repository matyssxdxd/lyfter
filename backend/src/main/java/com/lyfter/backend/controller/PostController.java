package com.lyfter.backend.controller;

import com.lyfter.backend.payload.request.PostRequest;
import com.lyfter.backend.payload.response.MessageResponse;
import com.lyfter.backend.service.PostCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostCRUDService postCRUDService;

    @Autowired
    public PostController(PostCRUDService postCRUDService) {
        this.postCRUDService = postCRUDService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllPosts() {
        try {
            return ResponseEntity.ok(postCRUDService.getAllPosts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getPostById(@RequestParam int id) {
        try {
            return ResponseEntity.ok(postCRUDService.getPostById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addPost(@RequestBody PostRequest postRequest) {
        try {
            postCRUDService.createPost(postRequest);
            return ResponseEntity.ok(new MessageResponse("Post created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deletePostById(@RequestParam int id) {
        try {
            postCRUDService.deletePost(id);
            return ResponseEntity.ok(new MessageResponse("Post deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
