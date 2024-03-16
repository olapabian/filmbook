package com.filmbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.filmbook.mapper",
		"com.filmbook.config",
		"com.filmbook.services.auth", // Include com.filmbook.services.auth package
		"com.filmbook.controllers",
		"com.filmbook.repositories"
})
public class FilmbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmbookApplication.class, args);
	}
}
