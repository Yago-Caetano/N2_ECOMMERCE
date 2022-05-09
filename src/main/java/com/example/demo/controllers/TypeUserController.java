package com.example.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.demo.models.TypeUser;
import com.example.demo.DAO.TypeUserDAO;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TypeUserController {
	
	@PostMapping("/typeUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody TypeUser type) 
	{
		try
		{
		    if (type == null) {
		        return ResponseEntity.notFound().build();
		    } else {
		    	
		    	TypeUserDAO dao = new TypeUserDAO();
		    	type.GenerateID();
		    	dao.insert(type);
		    	return ResponseEntity.ok(type);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	@GetMapping("/typeUser")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> GetData(@RequestParam(value = "id", defaultValue = "") String id) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (id.equals("") || id.equals(null)) {
		    	TypeUserDAO dao = new TypeUserDAO();
		    	var itens=dao.findAll();
		    	
		    	return ResponseEntity.ok(itens);
		    	
		    } else {

		    	TypeUserDAO dao = new TypeUserDAO();
		    	TypeUser tipo =dao.find(id);
		    	// Tipo não encontrado
		    	if (tipo==null)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	else
		    		return ResponseEntity.ok(tipo);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@PutMapping("/typeUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> Update(@RequestBody TypeUser type) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (type.getId() ==null) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    } else {

		    	TypeUserDAO dao = new TypeUserDAO();
		    	TypeUser tipo =dao.find(type.getId());
		    	// Tipo não encontrado
		    	if (tipo==null)
		    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	else
		    	{
		    		dao = new TypeUserDAO();
		    		dao.update(type);
		    		return ResponseEntity.ok(type);
		    	}
		    		
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}