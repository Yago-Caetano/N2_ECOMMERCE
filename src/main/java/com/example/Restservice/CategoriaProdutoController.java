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
import com.example.demo.Model.DAO.CategoriaProdutoDAO;
import com.example.demo.Model.DAO.PedidoDAO;
import com.example.demo.Model.DAO.ProdutoDAO;
import com.example.demo.Beans.*;
import com.example.demo.Beans.CategoriaProduto;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer.AbstractBase;

@RestController
public class CategoriaProdutoController {		
	
	@PostMapping("/categoria")
	public ResponseEntity create(@RequestBody CategoriaProduto categoria) 
	{
		try
		{
		    if (categoria == null) {
		        return ResponseEntity.notFound().build();
		    } else {
		    	
		    	
		    	CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
		    	dao.insert(categoria);
		    	//Procura o item para descobrir o ID
		    	dao = new CategoriaProdutoDAO();
		    	CategoriaProduto cp = dao.find(categoria.getId());
		    	return ResponseEntity.ok(cp);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	@GetMapping("/categoria")
	public ResponseEntity GetData(@RequestParam(value = "id", defaultValue = "0") Integer id) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (id == 0) {
		    	CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
		    	var itens=dao.findAll();
		    	return ResponseEntity.ok(itens);
		    	
		    } else {

		    	CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
		    	CategoriaProduto cp =dao.find(id);
		    	// Tipo não encontrado
		    	if (cp.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	else
		    		return ResponseEntity.ok(cp);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@PutMapping("/categoria")
	public ResponseEntity Update(@RequestBody CategoriaProduto categoria) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (categoria.getId() <= 0) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    } else {

		    	CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
		    	CategoriaProduto cp =dao.find(categoria.getId());
		    	// Tipo não encontrado
		    	if (cp.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	else
		    	{
		    		dao = new CategoriaProdutoDAO();
		    		dao.update(categoria);
		    		return ResponseEntity.ok(categoria);
		    	}
		    		
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@DeleteMapping("/categoria")
	public ResponseEntity DeleteData(@RequestParam(value = "id", defaultValue = "0") Integer id) {
		try
		{
			//Se não passar Id
		    if (id == 0) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    } else {

		    	CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
		    	CategoriaProduto cp = dao.find(id);
		    	// Pedido não encontrado
		    	if (cp.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    	dao.delete(id);
		    			    		    	
		    		return ResponseEntity.ok("Categoria Deletada");
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}