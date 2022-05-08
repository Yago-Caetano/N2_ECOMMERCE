package com.example.Restservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.Restservice.*;
import com.example.demo.DemoApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Beans.Endereco;
import com.example.demo.Beans.User;
import com.example.demo.Model.DAO.EnderecoDAO;
import com.example.demo.Model.DAO.UserDAO;

@RestController
public class EnderecoController {
		
	// ================ CRUD USUÁRIO ============================
	
		@GetMapping("/endereco")
		public ResponseEntity endereco(@RequestBody  int userID) throws Exception {				
			try {
				if (userID < 1) {
					return ResponseEntity.notFound().build();
				} else {			
					EnderecoDAO edao = new EnderecoDAO();
					edao.find(userID);
					Endereco u =  edao.find(userID);
					
					return ResponseEntity.ok(u);
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
			
		}	
		
		// Talvez o segundo requestBody não seja bom
		@PostMapping("/endereco")		
		public ResponseEntity create(@RequestBody  Endereco endereco, @RequestBody int userID) throws Exception {	
			try {
				if (userID < 1) {
					return ResponseEntity.notFound().build();
				} else {			
					EnderecoDAO edao = new EnderecoDAO();
					edao.insert(endereco, userID);
					Endereco e =  edao.find(userID);
					
					return ResponseEntity.ok(e);
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
		@PutMapping("/endereco")
		public ResponseEntity update(@RequestBody  Endereco endereco, int userID) throws Exception {						
			try {
				if (userID < 1) {
					return ResponseEntity.notFound().build();
				} else {			
					EnderecoDAO edao = new EnderecoDAO();
					edao.update(endereco);
					Endereco end =  edao.find(userID);
					
					return ResponseEntity.ok(end);
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
		@DeleteMapping("/endereco")
		public ResponseEntity delete(@RequestBody  int id) throws Exception {	
			try {
				if (id < 1) {
					return ResponseEntity.notFound().build();
				} else {
					
					EnderecoDAO edao = new EnderecoDAO();
					Endereco end = edao.find(id);
					edao.delete(id);
					
					// Eu deletei o usuário. Preciso mesmo retornar?
					return ResponseEntity.ok(null);
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
}