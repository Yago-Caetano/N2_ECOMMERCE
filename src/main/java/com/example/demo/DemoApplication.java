package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication(scanBasePackages={"com.example.Restservice","com.controller","com.example.config","com.service","com.DAO","com.model"})
@SpringBootApplication
//@RestController
public class DemoApplication {
	 
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
