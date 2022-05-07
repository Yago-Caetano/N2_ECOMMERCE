package com.example.Restservice;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.example.demo.Beans.TypeUser;
import com.example.demo.Model.DAO.TypeUserDAO;


@RestController
@CrossOrigin
public class TypeUserController {

	public static void main(String[] args) {
		SpringApplication.run(GreetingController.class, args);
	}
	
	
	@PostMapping("/typeUser")
	public ResponseEntity<?> create(@RequestBody TypeUser type) 
	{
		try
		{
		    if (type == null) {
		        return ResponseEntity.notFound().build();
		    } else {
		    	
		    	
		        /*URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		          .path("/{id}")
		          .buildAndExpand(type.getId())
		          .toUri();

		        return ResponseEntity.created(uri)
		          .body(type);*/
		    	
		    	TypeUserDAO dao = new TypeUserDAO();
		    	dao.insert(type);
		    	//Procura o item para descobrir o ID
		    	dao = new TypeUserDAO();
		    	TypeUser tipo = dao.find(type.getTipo());
		    	return ResponseEntity.ok(tipo);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	@GetMapping("/typeUser")
	public ResponseEntity<?> GetData(@RequestParam(value = "id", defaultValue = "0") Long id) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (id == 0) {
		    	TypeUserDAO dao = new TypeUserDAO();
		    	var itens=dao.findAll(null);
		    	return ResponseEntity.ok(itens);
		    	
		    } else {

		    	TypeUserDAO dao = new TypeUserDAO();
		    	TypeUser tipo =dao.find(id);
		    	// Tipo não encontrado
		    	if (tipo.getId()==0)
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
	public ResponseEntity<?> Update(@RequestBody TypeUser type) {
		try
		{
			//Retorna todos os tipos cadastrados
		    if (type.getId() <= 0) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
		    	
		    } else {

		    	TypeUserDAO dao = new TypeUserDAO();
		    	TypeUser tipo =dao.find(type.getId());
		    	// Tipo não encontrado
		    	if (tipo.getId()==0)
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