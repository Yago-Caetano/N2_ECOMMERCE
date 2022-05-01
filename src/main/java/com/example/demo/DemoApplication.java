package com.example.demo;

import com.example.Restservice.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Model.*;
import com.example.demo.Beans.Produto;
import com.example.demo.Model.DAO.ProdutoDAO;
import com.example.demo.Model.DAO.ProdutoRepository;
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

	/*@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}*/
	
	@GetMapping("/produtos")
	public Produto produtos() throws Exception {		
		ProdutoDAO pdao = new ProdutoDAO();
		return pdao.find(1);
	}
}
