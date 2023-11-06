package com.samuelclinton.fiaparkapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ApiDeParquimetrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDeParquimetrosApplication.class, args);
	}

}
