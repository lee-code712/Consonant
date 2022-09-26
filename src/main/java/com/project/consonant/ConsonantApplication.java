package com.project.consonant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ConsonantApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ConsonantApplication.class, args);
		System.out.println("Hello!!!!");
	}
 
}
