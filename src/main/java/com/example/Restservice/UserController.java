package com.example.Restservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.Restservice.*;
import com.example.demo.DemoApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Beans.User;
import com.example.demo.Model.DAO.UserDAO;

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
	
	// ================ CRUD USUÁRIO ============================
	
		@GetMapping("/user")
		public ResponseEntity user(@RequestBody  int id) throws Exception {				
			try {
				if (id < 1) {
					return ResponseEntity.notFound().build();
				} else {			
					UserDAO udao = new UserDAO();
					udao.find(id);
					User u =  udao.find(id);
					
					return ResponseEntity.ok(u);
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
			
		}
		
		@GetMapping("/users")
		public ResponseEntity users() throws Exception {	
			try {					
				return ResponseEntity.ok(new UserDAO().findAll());			
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
		@PostMapping("/user")
		public ResponseEntity insertUser(@RequestBody  User user) throws Exception {	
			try {
				if (user == null) {
					return ResponseEntity.notFound().build();
				} else {			
					UserDAO udao = new UserDAO();
					udao.insert(user);
					User u =  udao.find(user.getId());
					
					return ResponseEntity.ok(u);
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
		@PutMapping("/user")
		public ResponseEntity updateUser(@RequestBody  User user) throws Exception {						
			try {
				if (user == null) {
					return ResponseEntity.notFound().build();
				} else {			
					UserDAO udao = new UserDAO();
					udao.update(user);
					User u =  udao.find(user.getId());
					
					return ResponseEntity.ok(u);
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
		@DeleteMapping("/user")
		public ResponseEntity deleteUser(@RequestBody  int id) throws Exception {	
			try {
				if (id < 1) {
					return ResponseEntity.notFound().build();
				} else {
					
					UserDAO udao = new UserDAO();
					User u = udao.find(id);
					udao.delete(id);
					
					// Eu deletei o usuário. Preciso mesmo retornar?
					return ResponseEntity.ok(null);
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
}