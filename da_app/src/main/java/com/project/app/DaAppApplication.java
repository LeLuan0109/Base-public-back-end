package com.project.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class DaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaAppApplication.class, args);
	}

}
