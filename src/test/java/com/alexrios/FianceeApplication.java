package com.alexrios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FianceeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FianceeApplication.class, args);
	}

	@Bean
	public RestTemplate newRestTemplate() {
		return new RestTemplate();
	}
}
