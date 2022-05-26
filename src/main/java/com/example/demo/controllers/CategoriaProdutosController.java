package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.CategoriaProdutoDAO;
import com.example.demo.DAO.EnderecoDAO;
import com.example.demo.DAO.TypeUserDAO;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.CategoriaProduto;
import com.example.demo.models.EnderecoModel;
import com.example.demo.models.TypeUser;
import com.example.demo.models.User;
import com.example.demo.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CategoriaProdutosController {
	
	@PostMapping("/catProduct")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody  CategoriaProduto cat) throws Exception {	
		try {
			if (cat == null || cat.getCategoria().equals("") || cat.getCategoria().equals(null) ) {
				return ResponseEntity.badRequest().build();
			} else {
				
				CategoriaProdutoDAO cdao= new CategoriaProdutoDAO();
				cat.GenerateID();
				cdao.insert(cat);
				return ResponseEntity.ok(cat);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	// ================ CRUD USUÁRIO ============================
	
		
		@GetMapping("/catProduct")
		//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> GetData(@RequestParam(value = "id", defaultValue = "") String id) {
			try
			{

				//Retorna todos os tipos cadastrados
			    if (id.equals("") || id.equals(null)) {
			    	CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
			    	var itens=dao.findAll();
			    	
			    	return ResponseEntity.ok(itens);
			    	
			    } else {

			    	CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
			    	CategoriaProduto categoria =dao.find(id);
			    	// Tipo não encontrado
			    	if (categoria==null)
			    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
			    	else
			    		return ResponseEntity.ok(categoria);
			    
				}
				
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
					
		@PutMapping("/catProduct")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<?> updatecat(@RequestBody  CategoriaProduto cat) throws Exception {						
			try
			{
				//Retorna todos os tipos cadastrados
				if (cat.getId().equals("") || cat.getId().equals(null)) {
			    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
			    	
			    } else {

			    	CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
			    	CategoriaProduto categoria =dao.find(cat.getId());
			    	// Tipo não encontrado
			    	if (categoria==null)
			    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
			    	else
			    	{
			    		dao = new CategoriaProdutoDAO();
			    		dao.update(cat);
			    		return ResponseEntity.ok(cat);
			    	}
			    		
			    }
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
		@DeleteMapping("/catProduct")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<?> deleteUser(@RequestParam(value = "id", defaultValue = "") String id) {	
			try {
				if (id =="" || id==null) {
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
