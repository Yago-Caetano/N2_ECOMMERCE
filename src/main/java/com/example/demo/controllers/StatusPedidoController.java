package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.StatusPedidoDAO;
import com.example.demo.models.StatusPedidoModel;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class StatusPedidoController {

	@PostMapping("/statusOrder")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody StatusPedidoModel status) 
	{
		try
		{
		    if (status == null) {
		        return ResponseEntity.notFound().build();
		    } else {
		    	
		    	StatusPedidoDAO dao = new StatusPedidoDAO();
		    	status.GenerateID();
		    	dao.insert(status);
		    	return ResponseEntity.ok(status);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	@GetMapping("/statusOrder")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> GetData(@RequestParam(value = "id", defaultValue = "") String id) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (id.equals("") || id.equals(null)) {
		    	StatusPedidoDAO dao = new StatusPedidoDAO();
		    	var itens=dao.findAll();
		    	return ResponseEntity.ok(itens);
		    	
		    } else {

		    	StatusPedidoDAO dao = new StatusPedidoDAO();
		    	StatusPedidoModel status =dao.find(id);
		    	// Tipo não encontrado
		    	if (status==null)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	else
		    		return ResponseEntity.ok(status);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@PutMapping("/statusOrder")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> Update(@RequestBody StatusPedidoModel status) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (status.getId() ==null) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    } else {

		    	StatusPedidoDAO dao = new StatusPedidoDAO();
		    	StatusPedidoModel st =dao.find(status.getId());
		    	// Tipo não encontrado
		    	if (st==null)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	else
		    	{
		    		dao = new StatusPedidoDAO();
		    		dao.update(status);
		    		return ResponseEntity.ok(status);
		    	}
		    		
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
