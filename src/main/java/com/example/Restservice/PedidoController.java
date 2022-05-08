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

import com.example.demo.Beans.Pedido;
import com.example.demo.Beans.Produto;
import com.example.demo.Beans.TypeUser;
import com.example.demo.Model.DAO.PedidoDAO;
import com.example.demo.Model.DAO.ProdutoDAO;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer.AbstractBase;

@RestController
public class PedidoController {

	public static void main(String[] args) {
		SpringApplication.run(GreetingController.class, args);
	}
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@PostMapping("/pedido")
	public ResponseEntity create(@RequestBody Pedido pedido) 
	{
		try
		{
		    if (pedido == null) {
		        return ResponseEntity.notFound().build();
		    } else {
		    	
		    	
		    	PedidoDAO dao = new PedidoDAO();
		    	dao.insert(pedido);
		    	//Procura o item para descobrir o ID
		    	dao = new PedidoDAO();
		    	Pedido ped = dao.find(pedido.getId());
		    	return ResponseEntity.ok(ped);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	@GetMapping("/pedido")
	public ResponseEntity GetData(@RequestParam(value = "id", defaultValue = "0") Integer id) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (id == 0) {
		    	PedidoDAO dao = new PedidoDAO();
		    	var itens=dao.findAll();
		    	return ResponseEntity.ok(itens);
		    	
		    } else {

		    	PedidoDAO dao = new PedidoDAO();
		    	Pedido pedido =dao.find(id);
		    	// Tipo não encontrado
		    	if (pedido.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	else
		    		return ResponseEntity.ok(pedido);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@PutMapping("/pedido")
	public ResponseEntity Update(@RequestBody Pedido pedido) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (pedido.getId() <= 0) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    } else {

		    	PedidoDAO dao = new PedidoDAO();
		    	Pedido ped =dao.find(pedido.getId());
		    	// Tipo não encontrado
		    	if (ped.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	else
		    	{
		    		dao = new PedidoDAO();
		    		dao.update(pedido);
		    		return ResponseEntity.ok(pedido);
		    	}
		    		
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@DeleteMapping("/pedido")
	public ResponseEntity DeleteData(@RequestParam(value = "id", defaultValue = "0") Integer id) {
		try
		{
			//Se não passar Id
		    if (id == 0) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    } else {

		    	PedidoDAO dao = new PedidoDAO();
		    	Pedido pedido = dao.find(id);
		    	// Pedido não encontrado
		    	if (pedido.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    	dao.delete(id);
		    			    		    	
		    		return ResponseEntity.ok("Pedido Deletado");
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}