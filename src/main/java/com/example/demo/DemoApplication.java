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
}
