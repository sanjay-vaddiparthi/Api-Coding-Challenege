package com.hexaware;

import org.modelmapper.ModelMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CodingchallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingchallengeApplication.class, args);
	}
	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
