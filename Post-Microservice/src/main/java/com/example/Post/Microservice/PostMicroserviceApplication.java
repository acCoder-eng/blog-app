package com.example.Post.Microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PostMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostMicroserviceApplication.class, args);
	}

}
