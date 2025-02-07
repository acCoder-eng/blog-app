package com.example.Comment.Microservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    @Bean
    public Queue commentCreatedQueue() {
        return new Queue("comment-created-queue", true);
    }
} 