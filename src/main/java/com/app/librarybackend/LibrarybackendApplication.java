package com.app.librarybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class LibrarybackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarybackendApplication.class, args);
	}

}
