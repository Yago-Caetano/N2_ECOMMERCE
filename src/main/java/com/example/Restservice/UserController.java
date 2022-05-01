package com.example.Restservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.Restservice.*;
import com.example.demo.DemoApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Beans.User;

@RestController
public class UserController {

	public static void main(String[] args) {
		SpringApplication.run(GreetingController.class, args);
	}
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@PostMapping("/user")
	public ResponseEntity<User> create(@RequestBody User usuario) 
	    throws URISyntaxException {
	    if (usuario == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
	          .path("/{id}")
	          .buildAndExpand(usuario.getId())
	          .toUri();

	        return ResponseEntity.created(uri)
	          .body(usuario);
	    }
	}
}