package com.example.demo;

import com.example.Restservice.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Model.*;
import com.example.demo.Beans.Produto;
import com.example.demo.Model.DAO.ProdutoDAO;
import com.example.demo.Model.DAO.ProdutoRepository;
import com.example.demo.Model.DAO.UserDAO;
import com.example.demo.Beans.*;

@SpringBootApplication(scanBasePackages={"com.example.Restservice"})
@RestController
public class DemoApplication {
	 
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping("/produto")
	public Produto produto(@RequestBody  int id) throws Exception {		
		ProdutoDAO pdao = new ProdutoDAO();
		return pdao.find(id);
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
