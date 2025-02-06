package com.example.Post.Microservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=update",
    "spring.datasource.url=jdbc:postgresql://localhost:5432/blog_db",
    "spring.datasource.username=myuser",
    "spring.datasource.password=mypassword"
})
class PostMicroserviceApplicationTests {

	@Test
	void contextLoads() {
	}

}
