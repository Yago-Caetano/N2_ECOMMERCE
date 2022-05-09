package com.example.demo.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.EnderecoDAO;
import com.example.demo.DAO.OrdensDAO;
import com.example.demo.DAO.ProdutosDAO;
import com.example.demo.DAO.StatusPedidoDAO;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.EnderecoModel;
import com.example.demo.models.OrdensModel;
import com.example.demo.models.ProdutosModel;
import com.example.demo.models.User;
import com.example.demo.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class OrdensController {
	
	@PostMapping("/order")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody  OrdensModel ord) throws Exception {	
		try {
			if (ord == null || ord.getIdEndereco().equals("") || ord.getIdEndereco().equals(null) ) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço inválido!");
			} else {
				
				boolean FoundAddres=false;
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				UserDetailsImpl userTemp = (UserDetailsImpl)auth.getPrincipal();
				ord.setIdUsuario(userTemp.getId());	
				//Status pendente
				ord.setIdStatus("aoiuhwda23");
				
				StatusPedidoDAO sdao= new StatusPedidoDAO();
				ord.setStatus(sdao.find(ord.getIdStatus()));
								
				EnderecoDAO edao= new EnderecoDAO();
				var enderecos= edao.findAllAdressUser(ord.getIdUsuario());
				
				if (enderecos==null)
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço não pertence ao usuário atual");
					
	
				for(EnderecoModel end: enderecos)
				{
					if (end.getId().equals(ord.getIdEndereco()))
					{
						FoundAddres=true;
						break;
					}	
				}
				
				if(!FoundAddres)
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço não pertence ao usuário atual");
				
				ProdutosDAO epdao = new ProdutosDAO();
				for(ProdutosModel prod:ord.getProdutos())
				{
					if(prod.getQuantidade()<=0)
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).
								body("Quantidade de "+prod.getId()+" inválida!");
					
					epdao = new ProdutosDAO();
					ProdutosModel prodAux= epdao.find(prod.getId());
					
					if(prodAux==null)
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).
								body("Produto "+prod.getId()+" inválido!");
					else
					{
						prod.setNome(prodAux.getNome());
						prod.setDescricao(prodAux.getDescricao());
						prod.setDesconto(prodAux.getDesconto());
						prod.setPreco(prodAux.getPreco());
					}
						
				}
				OrdensDAO dao= new OrdensDAO();
				dao.insert(ord);			
				
				return ResponseEntity.ok(ord);
					
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@PostMapping("/addOrderProduct")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> addProduct(@RequestBody  OrdensModel ord) throws Exception {	
		try {
			if (ord == null || ord.getId().equals("") || ord.getId().equals(null) ) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
			} else {
				
				
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				UserDetailsImpl userTemp = (UserDetailsImpl)auth.getPrincipal();
			
				OrdensDAO dao= new OrdensDAO();
				OrdensModel ordemTemp=dao.find(ord.getId());
				
				if(ordemTemp==null )
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
				if(!ordemTemp.getIdUsuario().equals(userTemp.getId()))
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não pertence ao usuário solicitante");
						
				//Só pode adicionar item se a ordem estiver pendente
				if(!ordemTemp.getIdStatus().equals("aoiuhwda23"))
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).
							body("Não é permitido acrescentar itens em ordens"
							+ " que não estão pendentes");
				
				dao= new OrdensDAO();
				ordemTemp.setProdutos(dao.findAllProdutos(ordemTemp.getId()));
				
				for(ProdutosModel prodAtual:ordemTemp.getProdutos())
				{
					for(ProdutosModel prodNovos:ord.getProdutos())
					{
						//Produto já estava adicionado
						if(prodNovos.getId().equals(prodAtual.getId()))
							return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item: "+
									prodNovos.getId() + " já estava adicionado!");				
					}
				}
											
				ProdutosDAO epdao = new ProdutosDAO();
				for(ProdutosModel prod:ord.getProdutos())
				{
					epdao = new ProdutosDAO();
					ProdutosModel prodAux= epdao.find(prod.getId());
					
					if(prodAux==null)
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).
								body("Produto "+prod.getId()+" inválido!");
					
					if(prod.getQuantidade()<=0 || prod.getQuantidade()>prodAux.getQuantidade())
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).
								body("Quantidade de "+prod.getId()+" inválida!");
					
				
					else
					{
						prod.setNome(prodAux.getNome());
						prod.setDescricao(prodAux.getDescricao());
						prod.setDesconto(prodAux.getDesconto());
						prod.setPreco(prodAux.getPreco());
					}
						
				}
				dao= new OrdensDAO();
				dao.insertNewItensOrder(ord);			
				
				return ResponseEntity.ok(ord);
					
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@PutMapping("/ModifyOrderProduct")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> ModifyProduct(@RequestBody  OrdensModel ord) throws Exception {	
		try {
			if (ord == null || ord.getId().equals("") || ord.getId().equals(null) ) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
			} else {
				
				boolean ProdutoPertenceOrdem=false;
				int indiceProd=0;
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				UserDetailsImpl userTemp = (UserDetailsImpl)auth.getPrincipal();
			
				OrdensDAO dao= new OrdensDAO();
				OrdensModel ordemTemp=dao.find(ord.getId());
				
				//Verifica se a ordem pertence ao solicitante ou ordem não existe
				if(ordemTemp==null )
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
				if(!ordemTemp.getIdUsuario().equals(userTemp.getId()))
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não pertence ao usuário solicitante");
				
				//Só pode modificar item se a ordem estiver pendente
				if(!ordemTemp.getIdStatus().equals("aoiuhwda23"))
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).
							body("Não é permitido modificar itens em ordens"
							+ " que não estão pendentes");
				
				dao= new OrdensDAO();
				ordemTemp.setProdutos(dao.findAllProdutos(ordemTemp.getId()));
								
				for(ProdutosModel prodNovos:ord.getProdutos())
				{
					ProdutoPertenceOrdem=false;
					indiceProd=0;
					for(ProdutosModel prodAtual:ordemTemp.getProdutos())
					{
						//Produto já estava adicionado
						if(prodAtual.getId().equals(prodNovos.getId()))
						{
							ProdutoPertenceOrdem=true;
							break;
						}				
					}
					//Não é permitido modificar um item que não pertencia a lista
					if(!ProdutoPertenceOrdem)
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).
								body("Produto" + ordemTemp.getProdutos().get(indiceProd).getId()+
										" não encontrado na ordem "+
										ordemTemp.getId());
					else
					{
						ProdutosDAO pdao = new ProdutosDAO();
						ProdutosModel p= pdao.find(prodNovos.getId());
						if(prodNovos.getQuantidade()<=0 || prodNovos.getQuantidade()>p.getQuantidade() )
							return ResponseEntity.status(HttpStatus.BAD_REQUEST).
									body("Quantidade de "+prodNovos.getId()+" inválida!");

							prodNovos.setNome(ordemTemp.getProdutos().get(indiceProd).getNome());
							prodNovos.setDescricao(ordemTemp.getProdutos().get(indiceProd).getDescricao());
							prodNovos.setDesconto(ordemTemp.getProdutos().get(indiceProd).getDesconto());
							prodNovos.setPreco(ordemTemp.getProdutos().get(indiceProd).getPreco());
						
					}
					indiceProd++;
				}
											
				dao= new OrdensDAO();
				dao.UpdateItensOrder(ord);			
				
				return ResponseEntity.ok(ord);
					
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@DeleteMapping("/DelOrderProduct")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> DeleteProduct(@RequestBody  OrdensModel ord) throws Exception {	
		try {
			if (ord == null || ord.getId().equals("") || ord.getId().equals(null) ) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
			} else {
				
				boolean ProdutoPertenceOrdem=false;
				int indiceProd=0;
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				UserDetailsImpl userTemp = (UserDetailsImpl)auth.getPrincipal();
			
				OrdensDAO dao= new OrdensDAO();
				OrdensModel ordemTemp=dao.find(ord.getId());
				
				//Verifica se a ordem pertence ao solicitante ou ordem não existe
				if(ordemTemp==null )
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
				if(!ordemTemp.getIdUsuario().equals(userTemp.getId()))
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não pertence ao usuário solicitante");
				
				//Só pode modificar item se a ordem estiver pendente
				if(!ordemTemp.getIdStatus().equals("aoiuhwda23"))
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).
							body("Não é permitido deletar itens em ordens"
							+ " que não estão pendentes");
				
				dao= new OrdensDAO();
				ordemTemp.setProdutos(dao.findAllProdutos(ordemTemp.getId()));
								
				for(ProdutosModel prodNovos:ord.getProdutos())
				{
					ProdutoPertenceOrdem=false;
					indiceProd=0;
					for(ProdutosModel prodAtual:ordemTemp.getProdutos())
					{
						//Produto já estava adicionado
						if(prodAtual.getId().equals(prodNovos.getId()))
						{
							ProdutoPertenceOrdem=true;
							break;
						}				
					}
					//Não é permitido modificar um item que não pertencia a lista
					if(!ProdutoPertenceOrdem)
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).
								body("Produto" + ordemTemp.getProdutos().get(indiceProd).getId()+
										" não encontrado na ordem "+
										ordemTemp.getId());
				}
											
				dao= new OrdensDAO();
				dao.DeleteItensOrder(ord);			
				
				return ResponseEntity.ok(ord);
					
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
		
		@GetMapping("/order")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> GetData(@RequestParam(value = "id", defaultValue = "") String id,
				@RequestParam(value = "status", defaultValue = "") String status) {
			try
			{
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
				//se o usuario for administrador
				if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
				{
					//Retorna todos os tipos cadastrados
				    if (id.equals("") || id.equals(null)) {
				    	OrdensDAO dao = new OrdensDAO();
				    	
				    	if (status.equals("") || status.equals(null))
				    	{
				    		var itens=dao.findAll();
			    			return ResponseEntity.ok(itens);
				    	}
				    	else
				    	{
				    		var itens=dao.findAllByStatus(status);
			    			return ResponseEntity.ok(itens);
				    	}
				    		
				    	
				    } else {

				    	OrdensDAO dao = new OrdensDAO();
				    	OrdensModel ordem = new OrdensModel();
				    	
						if (status.equals("") || status.equals(null))
							ordem=dao.find(id);
						else
						{
							var or=dao.findAllByStatus(status);
							if (or!=null)
								ordem=or.get(0);
							else
								ordem=null;
						}
				    	
						if(ordem==null )
							return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
						
				    	StatusPedidoDAO sdao= new StatusPedidoDAO();
				    	ordem.setStatus(sdao.find(ordem.getIdStatus()));
				    	dao = new OrdensDAO();
				    	ordem.setProdutos(dao.findAllProdutos(id));
				    	return ResponseEntity.ok(ordem);
				    }
				}
				//se não administrador retorna somente o proprio usuario
				else
				{
					UserDetailsImpl user = (UserDetailsImpl)auth.getPrincipal();
					OrdensDAO dao = new OrdensDAO();
					
					ArrayList<OrdensModel> Ordens= new ArrayList<OrdensModel>();
					
					if (status.equals("") || status.equals(null))
						Ordens=dao.findAllByUser(user.getId());
					else
						Ordens=dao.findAllByUserAndStatus(user.getId(), status);
									
					
					if (Ordens!=null)
					{
						StatusPedidoDAO sdao;
						for(OrdensModel ord:Ordens)
						{
							sdao= new StatusPedidoDAO();							
							ord.setStatus(sdao.find(ord.getIdStatus()));
							dao = new OrdensDAO();
							ord.setProdutos(dao.findAllProdutos(ord.getId()));							
						}
					}
					
					return ResponseEntity.ok(Ordens); 
			
				}
				
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
		@GetMapping("/UserOrder")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> GetDataAdUser(@RequestParam(value = "idUser", defaultValue = "") String idUser,
				@RequestParam(value = "status", defaultValue = "") String status) {
			try
			{
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
				//se o usuario for administrador
				if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
				{
					//Retorna todos os tipos cadastrados
				    if (!idUser.equals("") && !idUser.equals(null)) {
				    	OrdensDAO dao = new OrdensDAO();
				    	
				    	ArrayList<OrdensModel> ordens = new ArrayList<OrdensModel>();
				    	
				    	if (status.equals("") || status.equals(null))
				    		ordens=dao.findAllByUser(idUser);
				    	
				    	else
				    		ordens=dao.findAllByUserAndStatus(idUser,status);
				    	
				    	StatusPedidoDAO sdao = new StatusPedidoDAO();
				    	
				    	for(OrdensModel ord:ordens)
				    	{
				    		dao = new OrdensDAO();
				    		ord.setProdutos(dao.findAllProdutos(ord.getId()));
				    		sdao = new StatusPedidoDAO();
				    		ord.setStatus(sdao.find(ord.getIdStatus()));
				    	}				    				    	
				    	
				    	return ResponseEntity.ok(ordens);
				    	
				    } else {
				    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário inválido!");
				    }
				}
				//se não administrador retorna somente o proprio usuario
				else
				{
					UserDetailsImpl user = (UserDetailsImpl)auth.getPrincipal();
					OrdensDAO dao = new OrdensDAO();
					
					ArrayList<OrdensModel> Ordens= new ArrayList<OrdensModel>();
					
					if (status.equals("") || status.equals(null))
						Ordens=dao.findAllByUser(user.getId());
					else
						Ordens=dao.findAllByUserAndStatus(user.getId(), status);
			    	
					StatusPedidoDAO sdao = new StatusPedidoDAO();
			    	for(OrdensModel ord:Ordens)
			    	{
			    		dao = new OrdensDAO();
			    		ord.setProdutos(dao.findAllProdutos(ord.getId()));
			    		sdao = new StatusPedidoDAO();
			    		ord.setStatus(sdao.find(ord.getIdStatus()));
			    	}
					return ResponseEntity.ok(Ordens);
				}
				
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
						
		@PutMapping("/OrderAddres")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> AdrresOrder(@RequestBody  OrdensModel ord) {						
			try
			{
				if (ord == null || ord.getId().equals("") || ord.getId().equals(null) ){
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
				} else {
					
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					UserDetailsImpl userTemp = (UserDetailsImpl)auth.getPrincipal();
				
					OrdensDAO dao= new OrdensDAO();
					OrdensModel ordemTemp=dao.find(ord.getId());
					
					//Verifica se a ordem pertence ao solicitante ou ordem não existe
					if(ordemTemp==null )
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
					if(!ordemTemp.getIdUsuario().equals(userTemp.getId()))
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não pertence ao usuário solicitante");
					
					ord.setIdUsuario(userTemp.getId());
					
					//Só pode modificar item se a ordem estiver pendente
					if(!ordemTemp.getIdStatus().equals("aoiuhwda23"))
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).
								body("Não é permitido modificar itens em ordens"
								+ " que não estão pendentes");
					
					ord.setIdStatus(ordemTemp.getIdStatus());
					
					boolean FoundAddres=false;
					
					EnderecoDAO edao= new EnderecoDAO();
					var enderecos= edao.findAllAdressUser(ord.getIdUsuario());
					
					if (enderecos==null)
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço não pertence ao usuário atual");
						
		
					for(EnderecoModel end: enderecos)
					{
						if (end.getId().equals(ord.getIdEndereco()))
						{
							FoundAddres=true;
							break;
						}	
					}
					
					if(!FoundAddres)
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço não pertence ao usuário atual");
					
						
					dao= new OrdensDAO();
					dao.update(ord);			
					
					return ResponseEntity.ok(ord);
						
				
				}
			}
			catch(Exception e)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		@PatchMapping("/FinOrder")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> FinalizarOrdem(@RequestParam(value = "id", defaultValue = "") String id) {	
			try {
				if (id == null || id.equals(null)) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
				} else {
					
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					UserDetailsImpl userTemp = (UserDetailsImpl)auth.getPrincipal();
				
					OrdensDAO dao= new OrdensDAO();
					OrdensModel ordemTemp=dao.find(id);
					
					//Verifica se a ordem pertence ao solicitante ou ordem não existe
					if(ordemTemp==null )
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
					if(!ordemTemp.getIdUsuario().equals(userTemp.getId()))
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não pertence ao usuário solicitante");
					
					//Só pode modificar item se a ordem estiver pendente
					if(!ordemTemp.getIdStatus().equals("aoiuhwda23"))
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).
								body("Não é permitido modificar itens em ordens"
								+ " que não estão pendentes");
					
					
					
					dao= new OrdensDAO();
					ordemTemp.setProdutos(dao.findAllProdutos(id));
					
					var result=dao.FinalizarOrder(ordemTemp);
					//Nenhum item retornou com falha
					if(result.isEmpty())
						return ResponseEntity.ok(ordemTemp);
					else
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).
								body("Produto "+ result.get(0).getId()+" com estoque insuficiente");
				
					
					
						
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		
	
		
		@DeleteMapping("/order")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public ResponseEntity<?> DeletarOrdem(@RequestParam(value = "id", defaultValue = "") String id) {	
			try {
				if (id == null || id.equals(null)) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
				} else {
					
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					UserDetailsImpl userTemp = (UserDetailsImpl)auth.getPrincipal();
				
					OrdensDAO dao= new OrdensDAO();
					OrdensModel ordemTemp=dao.find(id);
					
					//Verifica se a ordem pertence ao solicitante ou ordem não existe
					if(ordemTemp==null )
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não existe");
					if(!ordemTemp.getIdUsuario().equals(userTemp.getId()))
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem não pertence ao usuário solicitante");
					
					//Só pode modificar item se a ordem estiver pendente
					if(!ordemTemp.getIdStatus().equals("aoiuhwda23"))
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).
								body("Não é permitido modificar itens em ordens"
								+ " que não estão pendentes");
										
					
					dao= new OrdensDAO();
					dao.delete(id);
					return ResponseEntity.status(HttpStatus.OK).body("Pedido cancelado");
		
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}

}
