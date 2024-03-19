package com.spordee.user;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ApplicationUser {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationUser.class, args);
	}
}
