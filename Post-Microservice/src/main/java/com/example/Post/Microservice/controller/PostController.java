package com.example.Post.Microservice.controller;

import com.example.Post.Microservice.dto.PostCreateDTO;
import com.example.Post.Microservice.model.Post;
import com.example.Post.Microservice.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostCreateDTO postCreateDTO) {
        try {
            logger.debug("Creating post with data: {}", postCreateDTO);
            Post post = postService.createPost(postCreateDTO);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            logger.error("Error creating post: ", e);
            return ResponseEntity.internalServerError()
                .body("Error creating post: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
} 