package com.example.demo.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.models.TypeUser;
import com.example.demo.models.User;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.DAO.EnderecoDAO;
import com.example.demo.DAO.OrdensDAO;
import com.example.demo.DAO.TypeUserDAO;
import com.example.demo.DAO.UserDAO;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
	
	
	@Autowired
	  PasswordEncoder encoder;

	@PostMapping("/register")
	public ResponseEntity<?> create(@RequestBody  User user) throws Exception {	
		try {
			if (user == null || user.getIdTipoUsuario().equals("") || user.getIdTipoUsuario().equals(null) ) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário inválido");
			} else {
				TypeUserDAO tdao= new TypeUserDAO();
				TypeUser type= tdao.find(user.getIdTipoUsuario());
				if (type==null)
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de usuário inválido");
				
				UserDAO udao = new UserDAO();
				user.GenerateID();
				user.setSenha(encoder.encode(user.getSenha()));
				user.setStatusUsuario(true);
				udao.insert(user);
				user.setSenha("");				
				return ResponseEntity.ok(user);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	// ================ CRUD USUÁRIO ============================
	
		
		@GetMapping("/user")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> GetData(@RequestParam(value = "id", defaultValue = "") String id) {
			try
			{
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
				//se o usuario for administrador
				if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
				{
					//Retorna todos os tipos cadastrados
				    if (id.equals("")|| id.equals(null)) {
				    	UserDAO dao = new UserDAO();
				    	var itens=dao.findAll();
				    	for(User user:itens)
				    		user.setSenha("");
				    	
				    	if(itens==null)
				    		return ResponseEntity.status(HttpStatus.OK).body("{}");
				    	else
				    		return ResponseEntity.ok(itens);
				    	
				    } else {

				    	UserDAO dao = new UserDAO();
				    	User usuario =dao.find(id);
				    	// Tipo não encontrado
				    	if (usuario==null)
				    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
				    	else
				    	{
				    		TypeUserDAO tdao= new TypeUserDAO();
				    		usuario.setTipoUsuario(tdao.find(usuario.getIdTipoUsuario()));
				    		EnderecoDAO edao= new EnderecoDAO();
				    		usuario.setEnderecos(edao.findAllAdressUser(usuario.getId()));
				    		usuario.setSenha("");
				    		OrdensDAO Odao= new OrdensDAO();
				    		Odao.findAllByUser(id);
				    		return ResponseEntity.ok(usuario);
				    	}
				    		
				    }
				}
				//se não administrador retorna somente o proprio usuario
				else
				{
					UserDAO dao = new UserDAO();
					UserDetailsImpl user = (UserDetailsImpl)auth.getPrincipal();
					User usuario=dao.find(user.getId());
					usuario.setSenha("");
		    		OrdensDAO Odao= new OrdensDAO();
		    		Odao.findAllByUser(id);
		    		TypeUserDAO tdao= new TypeUserDAO();
		    		usuario.setTipoUsuario(tdao.find(usuario.getIdTipoUsuario()));
					return ResponseEntity.ok(usuario);
				}
				
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
						
		@PutMapping("/user")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> updateUser(@RequestBody  User user) throws Exception {						
			try
			{
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				user.setSenha(encoder.encode(user.getSenha()));
				//se o usuario for administrador
				if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
				{
					//Retorna todos os tipos cadastrados
				    if (!user.getId().equals("") && !user.getId().equals(null)) {
				    	UserDAO dao = new UserDAO();
				    	User usuario=dao.find(user.getId());
				    	
				    	if (usuario==null)
				    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário inválido");
				    	
				    	dao = new UserDAO();
				    	user.setStatusUsuario(true);
				    	dao.update(user);
				    	user.setSenha("");
				    	return ResponseEntity.ok(user);
				    	
				    } else {

				    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário inválido");
				    }
				}
				//se não administrador retorna somente o proprio usuario
				else
				{
					UserDAO dao = new UserDAO();
					UserDetailsImpl userTemp = (UserDetailsImpl)auth.getPrincipal();
					user.setId(userTemp.getId());					
					dao.update(user);
					user.setSenha("");
					return ResponseEntity.ok(user);
				}
				
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
		@DeleteMapping("/user")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<?> deleteUser(@RequestParam(value = "id", defaultValue = "") String id) {	
			try {
				if (id =="" || id==null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário inválido");
				} else {
					
					UserDAO udao = new UserDAO();
					User u = udao.find(id);
					if (u==null)
			    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário inválido");
					
					udao = new UserDAO();
					udao.delete(id);
					
					// Eu deletei o usuário. Preciso mesmo retornar?
					return ResponseEntity.ok(null);
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
}
