package com.filmbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.filmbook.mapper",
		"com.filmbook.config",
		"com.filmbook.services.auth",
		"com.filmbook.controllers",
		"com.filmbook.repositories",
		"com.filmbook.services.collectors",
		"com.filmbook.services.searches",
		"com.filmbook.services.userOperations",
		"com.filmbook.services.movieOperations",
		"com.filmbook.services.reviewOperations"
})
public class FilmbookApplication {

	public static void main(String[] args) {

		SpringApplication.run(FilmbookApplication.class, args);
		
	}
}
