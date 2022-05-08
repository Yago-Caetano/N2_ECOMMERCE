package com.example.demo.Model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Beans.Pedido;
import com.example.demo.Beans.Produto;
import com.example.demo.Beans.ProdutoPedido;
import com.example.demo.Model.DbUtil;
import com.example.demo.Model.*;

// https://github.com/danielscarvalho/FTT-PEOPLE-WEB-DB/blob/master/src/br/edu/cefsa/ftt/ec/dao/PeopleDao.java

/*
 * TODO 1. DONE - Criar tabela no banco de dados MySQL
 * TODO 2. DONE - Criar JavaBean para cada tabela do banco de dados MySQL
 * TODO 3. DONE - Criar a conex�o com o Banco de Dados (connection) DbUtil
 * TODO 4. DONE - Criar a interface Dao - Design Pattern - SQL para objetos vice-versa
 * TODO 5. DONE - Criar o Dao para a tabela "Client"
 * TODO 6. Criar WEB API CRUD para a tabela "Client"
 * TODO 7. DONE - Criar pasta libs e importar driver JDBC para banco de dados (.jar)
 * TODO 8. DONE - Importar .jar do JDBC no Java Build Path
 * 
 * Para N1 2B LP3 - Criar 5 tabelas...
 * Testar com Postman
 * Equipe de 1, 2 ou 3 alunos
 * 
 * 
 */

public class ProdutoPedidoDAO implements IRepositoryService<ProdutoPedido> {
	
	private Connection connection;
	
	//Constructor
	public ProdutoPedidoDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}

	@Override
	public void insert(ProdutoPedido produtoPedido) {
		
		CallableStatement callableStatement = null;
		
		try {
			
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO n2_ecommerce.tbPedidosxProdutos (IDPEDIDO, IDPRODUTO, QUANTIDADE, PRECO) VALUES (?,?, ?, ?)");
         
            preparedStatement.setString(1,produtoPedido.getIdPedido());
            preparedStatement.setString(2, produtoPedido.getIdProduto());
            preparedStatement.setInt(3, produtoPedido.getQuantidade());
            preparedStatement.setDouble(4, produtoPedido.getPreco());
            
            preparedStatement.executeUpdate();
            /*callableStatement = connection.prepareCall("{CALL spInsert_tbPedidosxProdutos(?,?,?)}");
            
            // Parameters 
            callableStatement.setString(1,produtoPedido.getIdPedido());
            callableStatement.setString(2, produtoPedido.getIdProduto());
            callableStatement.setInt(3, produtoPedido.getQuantidade());
            
            
            callableStatement.execute();
            
            callableStatement.close();
            connection.close();*/
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		
	}

	@Override
	public void update(ProdutoPedido produtoPedido) {
		
		CallableStatement callableStatement = null;
		
		try {
			PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE n2_ecommerce.tbPedidosxProdutos SET QUANTIDADE=? " 
                                                + "WHERE IDPEDIDO=? AND IDPRODUTO=?");
			
            
            // Parameters 
			preparedStatement.setInt(1, produtoPedido.getQuantidade());
			preparedStatement.setString(2,produtoPedido.getIdPedido());
			preparedStatement.setString(3, produtoPedido.getIdProduto());

            
            
			preparedStatement.execute();
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	} //update


	public void delete(String idPedido,String idProduto) {

		CallableStatement callableStatement = null;
		
		try {
            
			callableStatement = connection.prepareCall("{CALL spDelete_tbPedidosxProdutos(?,?)}");
            
            // Parameters 
            callableStatement.setString(1,idPedido);
            callableStatement.setString(2, idProduto);
            
            
            callableStatement.execute();
            

        } catch (SQLException e) {
        		e.printStackTrace();
        } //try
		
	} //delete 

	
	public ProdutoPedido find(String idPedido, String idProduto) {
		
		ProdutoPedido p = new ProdutoPedido();
		CallableStatement callableStatement = null;
		
    	try {
    		callableStatement = connection.prepareCall("{CALL spConsulta_tbPedidosxProdutos(?,?)}");
            
    		 // Parameters 
            callableStatement.setString(1,idPedido);
            callableStatement.setString(2, idProduto);
            
            ResultSet rs = callableStatement.executeQuery();

            if (rs.next()) {
            	p.setIdPedido(rs.getString("IDPEDIDO"));
                p.setIdProduto(rs.getString("IDPRODUTO"));
                p.setQuantidade(rs.getInt("QUANTIDADE"));
                p.setDesconto(rs.getDouble("DESCONTO"));
                p.setPreco(rs.getDouble("PRECO"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

        return p;
		
	} //find


	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProdutoPedido find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ProdutoPedido> findAll(ProdutoPedido obj) {
		// TODO Auto-generated method stub
		return null;
	}

} //ProdutoDao
