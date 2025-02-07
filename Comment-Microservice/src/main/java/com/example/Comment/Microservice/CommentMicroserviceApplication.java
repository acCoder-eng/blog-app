package com.example.Comment.Microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
public class CommentMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentMicroserviceApplication.class, args);
	}

}
