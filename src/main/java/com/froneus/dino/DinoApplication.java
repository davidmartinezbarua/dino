package com.froneus.dino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DinoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DinoApplication.class, args);
	}

}
