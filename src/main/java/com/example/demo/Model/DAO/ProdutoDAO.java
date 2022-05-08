package com.example.demo.Model.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Beans.Produto;
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

public class ProdutoDAO implements IRepositoryService<Produto> {
	
	private Connection connection;
	
	//Constructor
	public ProdutoDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}

	@Override
	public void insert(Produto produto) {

		try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO tbprodutos (ID, NOME, PRECO, DESCRICAO, FOTO, QUANTIDADE, DESCONTO, IDCATEGORIA) VALUES (?,?, ?, ?, ?, ?, ?, ?)");
            
            // Parameters start with 1
            preparedStatement.setInt(1, produto.getId());
            preparedStatement.setString(2, produto.getNome());
            preparedStatement.setDouble(3, produto.getPreco());
            preparedStatement.setString(4, produto.getDescricao());
            preparedStatement.setBytes(5, produto.getFoto());
            preparedStatement.setInt(6, produto.getQuantidade());
            preparedStatement.setDouble(7, produto.getDesconto());          
            preparedStatement.setInt(8, produto.getIdCategoria());
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		
	}

	@Override
	public void update(Produto produto) {
		
		try {
			//Java 13 - text block -  """   """
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE tbprodutos SET NOME=?, " 
                    		                           + "PRECO=?, "
                    		                           + "DESCRICAO=?, "
                    		                           + "FOTO=?, "
                    		                           + "QUANTIDADE=?, "
                    		                           + "DESCONTO=?, " 
                    		                           + "IDCATEGORIA=? "  
                                                + "WHERE ID=?");
            
            // Parameters start with 1
            // preparedStatement previne SQL Injection...
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setDouble(2, produto.getPreco());
            preparedStatement.setString(3, produto.getDescricao());
            preparedStatement.setBytes(4, produto.getFoto());
            preparedStatement.setInt(5, produto.getQuantidade());
            preparedStatement.setDouble(6, produto.getDesconto());  
            preparedStatement.setInt(7, produto.getIdCategoria());

            preparedStatement.setInt(8, produto.getId());
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	} //update

	public void delete(int id) {

		try {
            
        	PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM tbprodutos WHERE ID=?");
            
            // Parameters start with 1
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
        		e.printStackTrace();
        } //try
		
	} //delete 

	public Produto find(String id) {
		
		Produto p = new Produto();
        
    	try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM n2_ecommerce.tbprodutos WHERE ID=?");
            
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                p.setId(rs.getInt("ID"));
                p.setNome(rs.getString("NOME"));
                p.setPreco(rs.getDouble("PRECO"));
                p.setDescricao(rs.getString("DESCRICAO"));
                p.setFoto(rs.getBytes("FOTO"));
                p.setQuantidade(rs.getInt("QUANTIDADE"));               
                p.setDesconto(rs.getDouble("DESCONTO"));              
                p.setIdCategoria(rs.getInt("IDCATEGORIA"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

        return p;
		
	} //find

	@Override
	public ArrayList<Produto> findAll(Produto produto) {
		//Ajustar para enviar os dados de forma paginada, usar fun��o SQL "LIMIT" do MySQL
		
		ArrayList<Produto> pList = new ArrayList<Produto>();
        
        try {
        	
            Statement statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT * FROM tbprodutos");
            
            while (rs.next()) {
                
            	Produto p = new Produto();
                
            	p.setId(rs.getInt("ID"));
                p.setNome(rs.getString("NOME"));
                p.setPreco(rs.getDouble("PRECO"));
                p.setDescricao(rs.getString("DESCRICAO"));
                p.setFoto(rs.getBytes("FOTO"));
                p.setQuantidade(rs.getInt("QUANTIDADE"));               
                p.setDesconto(rs.getDouble("DESCONTO"));
                p.setIdCategoria(rs.getInt("IDCATEGORIA"));

                pList.add(p);
                
            } //while
            
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

        return pList;
		
	} //findAll
	
	public int count() {
		
		int count = -1;
		
		try {
			
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT COUNT(1) QTD FROM tbprodutos");
            
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                count = rs.getInt("QTD");
                
            } //if
            
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

		return count;
		
	} //count
	
	public int maxId() {
		
		int maxId = -1;
		
		try {
			
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT MAX(ID) MAX_ID FROM tbprodutos");
            
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("MAX_ID");
                
            } //if
            
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

		return maxId;
		
	} //maxId

	@Override
	public Produto find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

} //ProdutoDao
