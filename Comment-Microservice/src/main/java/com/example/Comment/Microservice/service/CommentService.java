package com.example.Comment.Microservice.service;

import com.example.Comment.Microservice.dto.CommentCreateDTO;
import com.example.Comment.Microservice.dto.CommentResponseDTO;
import com.example.Comment.Microservice.model.Comment;
import com.example.Comment.Microservice.repository.CommentRepository;
import com.example.Comment.Microservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public CommentResponseDTO createComment(CommentCreateDTO commentCreateDTO) {
        Comment comment = new Comment();
        comment.setPostId(commentCreateDTO.getPostId());
        comment.setUserId(commentCreateDTO.getUserId());
        comment.setContent(commentCreateDTO.getContent());
        
        Comment savedComment = commentRepository.save(comment);
        
        // Send notification message
        String message = String.format("%d:%d:%d", 
            savedComment.getUserId(), 
            savedComment.getPostId(), 
            savedComment.getId());
        rabbitTemplate.convertAndSend("comment-created-queue", message);
        
        return convertToDTO(savedComment);
    }

    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CommentResponseDTO> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CommentResponseDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        return convertToDTO(comment);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }

    private CommentResponseDTO convertToDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setPostId(comment.getPostId());
        dto.setUserId(comment.getUserId());
        dto.setContent(comment.getContent());
        return dto;
    }
} 