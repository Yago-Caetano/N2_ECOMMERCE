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

import com.example.demo.DAO.EnderecoDAO;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.EnderecoModel;
import com.example.demo.models.User;
import com.example.demo.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class EnderecoController {
	
	@PostMapping("/addres")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody  EnderecoModel end) throws Exception {	
		try {
			if (end == null || end.getIdUsuario().equals("") || end.getIdUsuario().equals(null) ) {
				return ResponseEntity.badRequest().build();
			} else {
				UserDAO udao= new UserDAO();
				User user= udao.find(end.getIdUsuario());
				
				if (user==null)
					return ResponseEntity.notFound().build();
				
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				//Não é administrador, garante que o usuario associado será o proprio usuario que chamou
				if (auth != null && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
				{
					UserDetailsImpl userTemp = (UserDetailsImpl)auth.getPrincipal();
					end.setIdUsuario(userTemp.getId());
				}
				
				EnderecoDAO edao = new EnderecoDAO();
				edao.insert(end);			
				return ResponseEntity.ok(end);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	// ================ CRUD USUÁRIO ============================
	
		
		@GetMapping("/addres")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> GetData(@RequestParam(value = "idEndeco", defaultValue = "") String idEndeco) {
			try
			{
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
				//se o usuario for administrador
				if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
				{
					//Retorna todos os tipos cadastrados
				    if (idEndeco.equals("") || idEndeco.equals(null)) {
				    	EnderecoDAO dao = new EnderecoDAO();
				    	var itens=dao.findAll();
				    	return ResponseEntity.ok(itens);
				    	
				    } else {

				    	EnderecoDAO dao = new EnderecoDAO();
				    	var endereco=dao.find(idEndeco);
				    	return ResponseEntity.ok(endereco);
				    }
				}
				//se não administrador retorna somente o proprio usuario
				else
				{
					UserDetailsImpl user = (UserDetailsImpl)auth.getPrincipal();
					EnderecoDAO dao = new EnderecoDAO();
					var endereco=dao.find(idEndeco);
					if (endereco.getIdUsuario().equals(user.getId()))
						return ResponseEntity.ok(endereco);
					
					else
						return ResponseEntity.notFound().build();
				}
				
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		@GetMapping("/UserAddres")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> GetDataAdUser(@RequestParam(value = "idUser", defaultValue = "") String idUser) {
			try
			{
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
				//se o usuario for administrador
				if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
				{
					//Retorna todos os tipos cadastrados
				    if (!idUser.equals("") && !idUser.equals(null)) {
				    	EnderecoDAO dao = new EnderecoDAO();
				    	var itens=dao.findAllAdressUser(idUser);
				    	return ResponseEntity.ok(itens);
				    	
				    } else {
				    	return ResponseEntity.badRequest().build();
				    }
				}
				//se não administrador retorna somente o proprio usuario
				else
				{
					UserDetailsImpl user = (UserDetailsImpl)auth.getPrincipal();
					EnderecoDAO dao = new EnderecoDAO();
			    	var itens=dao.findAllAdressUser(user.getId());
					return ResponseEntity.ok(itens);
				}
				
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
						
		@PutMapping("/addres")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> updateUser(@RequestBody  EnderecoModel end) throws Exception {						
			try
			{
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				//user.setSenha(encoder.encode(user.getSenha()));
				//se o usuario for administrador
				if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
				{
					//Retorna todos os tipos cadastrados
				    if (!end.getId().equals("") && !end.getId().equals(null)) {
				    	EnderecoDAO dao = new EnderecoDAO();
				    	EnderecoModel endereco=dao.find(end.getId());
				    	
				    	if (end.getIdUsuario().equals("") || end.getIdUsuario().equals(null))
				    		return ResponseEntity.notFound().build();
				    	
				    	dao = new EnderecoDAO();
				    	dao.update(end);
				    	return ResponseEntity.ok(end);
				    	
				    } else {

				    	return ResponseEntity.badRequest().build();
				    }
				}
				//se não administrador retorna somente o proprio usuario
				else
				{
					EnderecoDAO dao = new EnderecoDAO();
			    	EnderecoModel endereco=dao.find(end.getId());
					UserDetailsImpl userTemp = (UserDetailsImpl)auth.getPrincipal();
					
					if (endereco!=null && endereco.getIdUsuario().equals(userTemp.getId()))
					{
						dao = new EnderecoDAO();
						dao.update(end);
						return ResponseEntity.ok(end);
					}
					else
						return ResponseEntity.notFound().build();
				}
				
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
		@DeleteMapping("/addres")
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
