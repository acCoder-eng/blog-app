package com.example.Post.Microservice.service;

import com.example.Post.Microservice.dto.PostCreateDTO;
import com.example.Post.Microservice.model.Post;
import com.example.Post.Microservice.repository.PostRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Post createPost(PostCreateDTO postCreateDTO) {
        Post post = new Post();
        post.setUserId(postCreateDTO.getUserId());
        post.setTitle(postCreateDTO.getTitle());
        post.setContent(postCreateDTO.getContent());
        
        Post savedPost = postRepository.save(post);
        
        // Send notification message
        String message = String.format("%d:%d:%s", 
            savedPost.getUserId(), 
            savedPost.getId(), 
            savedPost.getTitle());
        rabbitTemplate.convertAndSend("post-created-queue", message);
        
        return savedPost;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

} 