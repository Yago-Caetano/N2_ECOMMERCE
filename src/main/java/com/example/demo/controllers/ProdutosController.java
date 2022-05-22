package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.DAO.CategoriaProdutoDAO;
import com.example.demo.DAO.ProdutosDAO;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.CategoriaProduto;
import com.example.demo.models.ProdutosModel;
import com.example.demo.models.User;
import com.example.demo.controllers.services.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProdutosController {

	@PostMapping("/products")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody  ProdutosModel prod) throws Exception {	
		try {
			if (prod == null || prod.getIdCategoria().equals("") || prod.getIdCategoria().equals(null) ) {
				return ResponseEntity.badRequest().build();
			} else {
				CategoriaProdutoDAO dao= new CategoriaProdutoDAO();
				CategoriaProduto cat= dao.find(prod.getIdCategoria());
				
				if (!prod.getFotoEmBase64().equals(null) && !prod.getFotoEmBase64().equals(""))
				{				
					prod.setFoto(ImageConversionService.convertToBlob(prod.getFotoEmBase64()));
				}
				
				if (cat==null)
					return ResponseEntity.notFound().build();
				
				ProdutosDAO pdao = new ProdutosDAO();
				pdao.insert(prod);			
				return ResponseEntity.ok(prod);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	// ================ CRUD USUÁRIO ============================
	
		
		@GetMapping("/products")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> GetData(@RequestParam(value = "id", defaultValue = "") String id) {
			try
			{
				//Retorna todos os tipos cadastrados
			    if (id.equals("") || id.equals(null)) {
			    	ProdutosDAO dao = new ProdutosDAO();
			    	var itens=dao.findAll();
			    	if (itens!=null)
			    	{
				    	for(ProdutosModel prods:itens)
				    	{
				    		CategoriaProdutoDAO cdao = new CategoriaProdutoDAO();
				    		CategoriaProduto cat = new CategoriaProduto();
				    		cat=cdao.find(prods.getIdCategoria());
				    		prods.setCategoria(cat);
				    		
				    		if (!prods.getFoto().equals(null))
				    		{
				    			prods.setFotoEmBase64(ImageConversionService.convertToBase64(prods.getFoto()));
				    		}
				    	}
				    	
			    	}
			    	
			    	if(itens==null)
			    		return ResponseEntity.status(HttpStatus.OK).body("{}");
			    	
			    	else
			    		return ResponseEntity.ok(itens);
			    	
			    } else {

			    	ProdutosDAO dao = new ProdutosDAO();
			    	ProdutosModel prod =dao.find(id);
			    	// Tipo não encontrado
			    	if (prod==null)
			    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
			    	else
			    	{
			    		CategoriaProdutoDAO cdao = new CategoriaProdutoDAO();
			    		CategoriaProduto cat = new CategoriaProduto();
			    		cat=cdao.find(prod.getIdCategoria());
			    		prod.setCategoria(cat);
			    		
			    		if (prod.getFoto()!=null)
			    		{
			    			prod.setFotoEmBase64(ImageConversionService.convertToBase64(prod.getFoto()));
			    		}
			    		
			    		return ResponseEntity.ok(prod);
			    	}
			    		
			    }				
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
						
		@PutMapping("/products")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<?> updateUser(@RequestBody  ProdutosModel prod) throws Exception {						
			try
			{
				if (prod == null || prod.getId().equals("") || prod.getId().equals(null) ) {
					return ResponseEntity.badRequest().build();
				} else {
					CategoriaProdutoDAO dao= new CategoriaProdutoDAO();
					CategoriaProduto cat= dao.find(prod.getIdCategoria());
					
					if (cat==null)
						return ResponseEntity.notFound().build();
					
					if (!prod.getFotoEmBase64().equals(null) && !prod.getFotoEmBase64().equals(""))
					{				
						prod.setFoto(ImageConversionService.convertToBlob(prod.getFotoEmBase64()));
					}
					
					ProdutosDAO pdao = new ProdutosDAO();
					pdao.update(prod);			
					return ResponseEntity.ok(prod);
				}
				
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
		@DeleteMapping("/products")
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
