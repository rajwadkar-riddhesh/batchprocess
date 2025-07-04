package com.example.Batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BatchProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchProcessingApplication.class, args);
	}
}

