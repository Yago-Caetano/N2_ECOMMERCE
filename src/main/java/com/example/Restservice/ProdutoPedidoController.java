package com.example.Restservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

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
import com.example.demo.Beans.ProdutoPedido;
import com.example.demo.Beans.TypeUser;
import com.example.demo.Model.DAO.PedidoDAO;
import com.example.demo.Model.DAO.ProdutoDAO;
import com.example.demo.Model.DAO.ProdutoPedidoDAO;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer.AbstractBase;

@RestController
public class ProdutoPedidoController {

	public static void main(String[] args) {
		SpringApplication.run(GreetingController.class, args);
	}
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@PostMapping("/produtoPedido")
	public ResponseEntity create(@RequestBody ProdutoPedido produtoPedido) throws Exception 
	{
		try
		{
			//Context context = new InitialContext();
			

			
		    if (produtoPedido == null) {
		        return ResponseEntity.notFound().build();
		    } else {
				    	
		    	ProdutoPedidoDAO dao = new ProdutoPedidoDAO();
		    	dao.insert(produtoPedido);
		    	//Procura o item para descobrir o ID
		    	/*dao = new CarrinhoDAO();
		    	Carrinho car = dao.find(carrinho.getIdPedido());
		    	return ResponseEntity.ok(car);*/
		    	
		    	
		    	return ResponseEntity.ok("Produto do pedido inserido");	    	

		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
						
		
	}
	/*@GetMapping("/produto")
	public ResponseEntity GetData(@RequestParam(value = "id", defaultValue = "0") Integer id) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (id == 0) {
		    	ProdutoDAO dao = new ProdutoDAO();
		    	var itens=dao.findAll(null);
		    	return ResponseEntity.ok(itens);
		    	
		    } else {

		    	ProdutoDAO dao = new ProdutoDAO();
		    	Produto produto =dao.find(id);
		    	// Tipo não encontrado
		    	if (produto.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	else
		    		return ResponseEntity.ok(produto);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}*/
	@PutMapping("/produtoPedido")
	public ResponseEntity Update(@RequestBody ProdutoPedido produtoPedido) {
		try
		{
			//Retorna todos os tipos cadastrados

			ProdutoPedidoDAO dao = new ProdutoPedidoDAO();
			ProdutoPedido prod =dao.find(produtoPedido.getIdPedido(),produtoPedido.getIdProduto());
		    	// Tipo não encontrado
		    	if (prod.getIdPedido()==null)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	else
		    	{
		    		dao = new ProdutoPedidoDAO();
		    		dao.update(produtoPedido);
		    		return ResponseEntity.ok(produtoPedido);
		    	}
		    		
		    
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	/*@DeleteMapping("/produto")
	public ResponseEntity DeleteData(@RequestParam(value = "id", defaultValue = "0") Integer id) {
		try
		{
			//Se não passar Id
		    if (id == 0) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    } else {

		    	ProdutoDAO dao = new ProdutoDAO();
		    	Produto produto = dao.find(id);
		    	// Produto não encontrado
		    	if (produto.getId()==0)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    	dao.delete(id);
		    			    		    	
		    		return ResponseEntity.ok("Produto Deletado");
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
*/}