package com.example.Notification.Microservice.listener;

import com.example.Notification.Microservice.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "post-created-queue")
    public void handlePostCreated(String message) {
        // message format: "userId:postId:title"
        String[] parts = message.split(":");
        Long userId = Long.parseLong(parts[0]);
        String title = parts[2];
        
        notificationService.createNotification(
            userId,
            "Yeni bir blog yazısı oluşturuldu: " + title,
            "POST_CREATED"
        );
    }

    @RabbitListener(queues = "comment-created-queue")
    public void handleCommentCreated(String message) {
        // message format: "userId:postId:commentId"
        String[] parts = message.split(":");
        Long userId = Long.parseLong(parts[0]);
        Long postId = Long.parseLong(parts[1]);
        
        notificationService.createNotification(
            userId,
            "Blog yazınıza yeni bir yorum yapıldı. Post ID: " + postId,
            "COMMENT_CREATED"
        );
    }
} 