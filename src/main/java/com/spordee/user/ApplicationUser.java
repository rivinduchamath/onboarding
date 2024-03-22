package com.spordee.user;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
//@ConfigurationPropertiesScan("com.spordee.user.configurations")
public class ApplicationUser {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationUser.class, args);
	}
}
