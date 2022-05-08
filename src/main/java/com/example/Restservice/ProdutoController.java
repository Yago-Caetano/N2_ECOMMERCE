package com.example.Restservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Beans.Produto;
import com.example.demo.Beans.TypeUser;
import com.example.demo.Model.DAO.ProdutoDAO;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer.AbstractBase;

@RestController
public class ProdutoController {

	public static void main(String[] args) {
		SpringApplication.run(GreetingController.class, args);
	}
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@PostMapping("/produto")
	public ResponseEntity create(@RequestBody Produto produto) 
	{
		try
		{
		    if (produto == null) {
		        return ResponseEntity.notFound().build();
		    } else {
		    	
		    	
		        /*URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		          .path("/{id}")
		          .buildAndExpand(type.getId())
		          .toUri();
		        return ResponseEntity.created(uri)
		          .body(type);*/
		    	
		    	ProdutoDAO dao = new ProdutoDAO();
		    	dao.insert(produto);
		    	//Procura o item para descobrir o ID
		    	dao = new ProdutoDAO();
		    	Produto prod = dao.find(produto.getId());
		    	return ResponseEntity.ok(prod);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	@GetMapping("/produto")
	public ResponseEntity GetData(@RequestParam(value = "id", defaultValue = "0") Integer id) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (id == 0) {
		    	ProdutoDAO dao = new ProdutoDAO();
		    	var itens=dao.findAll();
		    	return ResponseEntity.ok(itens);
		    	
		    } else {

		    	ProdutoDAO dao = new ProdutoDAO();
		    	Produto produto =dao.find(id);
		    	// Tipo n�o encontrado
		    	if (produto.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id n�o encontrado");
		    	else
		    		return ResponseEntity.ok(produto);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@PutMapping("/produto")
	public ResponseEntity Update(@RequestBody Produto produto) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (produto.getId() <= 0) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id n�o encontrado");
		    	
		    } else {

		    	ProdutoDAO dao = new ProdutoDAO();
		    	Produto prod =dao.find(produto.getId());
		    	// Tipo n�o encontrado
		    	if (prod.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id n�o encontrado");
		    	else
		    	{
		    		dao = new ProdutoDAO();
		    		dao.update(produto);
		    		return ResponseEntity.ok(produto);
		    	}
		    		
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@DeleteMapping("/produto")
	public ResponseEntity DeleteData(@RequestParam(value = "id", defaultValue = "0") Integer id) {
		try
		{
			//Se n�o passar Id
		    if (id == 0) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id n�o encontrado");
		    	
		    } else {

		    	ProdutoDAO dao = new ProdutoDAO();
		    	Produto produto = dao.find(id);
		    	// Produto n�o encontrado
		    	if (produto.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id n�o encontrado");
		    	
		    	dao.delete(id);
		    			    		    	
		    		return ResponseEntity.ok("Produto Deletado");
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}