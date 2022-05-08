package com.example.Restservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Beans.Carrinho;
import com.example.demo.Beans.Pedido;
import com.example.demo.Beans.Produto;
import com.example.demo.Beans.ProdutoPedido;
import com.example.demo.Beans.TypeUser;
import com.example.demo.Model.DAO.PedidoDAO;
import com.example.demo.Model.DAO.ProdutoDAO;
import com.example.demo.Model.DAO.ProdutoPedidoDAO;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer.AbstractBase;

@RestController
@EnableTransactionManagement
@Transactional
public class CarrinhoController {

	public static void main(String[] args) {
		SpringApplication.run(GreetingController.class, args);
	}
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();	

    //private UserTransaction userTransaction = new UserTransactionImp();
	
	@PostMapping("/carrinho")
	@Transactional
	public ResponseEntity create(@RequestBody List<Carrinho> carrinhos) 
	{
		
		
		   Pedido pedido = new Pedido();
		   Date data = new Date();
           pedido.setDataPedido(data);
           PedidoDAO pedidoDAO;
		try {
			
			pedidoDAO = new PedidoDAO();
			//status do pedido
	           pedido.setIdStatus(1);

	          /* //Endereço 
	           EnderecoViewModel endereco = new EnderecoViewModel();
	           endereco.Id = idEndereco;*/

	           //Usuario
	          /* UsuarioSimplificadoViewModel user = new UsuarioSimplificadoViewModel();
	           user.Id = HelperControllers.GetUserLogadoID(HttpContext.Session);*/

	           pedido.setIdEndereco(1);
	           pedido.setIdUsuario(1);
	           
	           //pedido.setIdUsuario(user.Id);
	           int maxId = 1;
	           maxId = pedidoDAO.maxId();
	           
	           pedido.setId(maxId + 1);
	           pedidoDAO.insert(pedido);
	           //String idNovoPedido = pedidoDAO.getId(pedido);

	           String idNovoPedido = String.valueOf(pedidoDAO.maxId()); 
	           ProdutoPedidoDAO itemDAO = new ProdutoPedidoDAO();
	           

	           //var carrinho = ObtemCarrinhoNaSession();
	           for (Carrinho carrinho : carrinhos)
	           {
	               ProdutoPedido item = new ProdutoPedido();
	               ProdutoDAO produtoDAO= new ProdutoDAO();
	               Produto p = new Produto();
	               
	               p = produtoDAO.find(carrinho.getProdutoId());
	               Double precoTotal = 0.0;
	               precoTotal = p.getPreco() * carrinho.getQuantidade();
	               

	               item.setIdPedido(idNovoPedido);
	               item.setIdProduto(carrinho.getProdutoId());
	               item.setQuantidade(carrinho.getQuantidade());
	               item.setPreco(precoTotal);
	               
	               if(p.getQuantidade() - item.getQuantidade() < 0) {
	       			pedidoDAO.delete(Integer.valueOf(idNovoPedido));
	            	   throw new Exception();
	               }else {
	            	   p.setQuantidade(p.getQuantidade() - item.getQuantidade());
	            	   produtoDAO.update(p);
	               }
		               
	               
	               itemDAO.insert(item);
	           }
	           
	           return ResponseEntity.ok("Carrinho inserido");	  
	           
		} catch (Exception e) {
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

           
           
       
           
		/*try
		{
		    if (pedido == null) {
		        return ResponseEntity.notFound().build();
		    } else {
		    	
		    	PedidoDAO dao = new PedidoDAO();
		    	dao.insert(pedido);
		    	//Procura o item para descobrir o ID
		    	dao = new PedidoDAO();
		    	Pedido ped = dao.find(pedido.getId());
		    	return ResponseEntity.ok(ped);
		    }
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}*/
		
	}
	
}