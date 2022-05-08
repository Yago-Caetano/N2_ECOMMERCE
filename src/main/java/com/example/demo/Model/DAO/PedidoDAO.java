package com.example.demo.Model.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Beans.Pedido;
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

public class PedidoDAO implements IRepositoryService<Pedido> {
	
	private Connection connection;
	
	//Constructor
	public PedidoDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}

	@Override
	public void insert(Pedido pedido) {

		try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO n2_ecommerce.tbpedidos (ID, IDSTATUS, IDUSUARIO, IDENDERECO, DATA) VALUES (?,?, ?, ?, ?)");
            
            // Parameters start with 1
            preparedStatement.setInt(1, pedido.getId());
            preparedStatement.setInt(2, pedido.getIdStatus());
            preparedStatement.setInt(3, pedido.getIdUsuario());
            preparedStatement.setInt(4, pedido.getIdEndereco());
            
            java.util.Date dt = pedido.getDataPedido();
            java.sql.Date d = new java.sql.Date (dt.getTime());
            
            preparedStatement.setDate(5, d);

            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		
	}

	@Override
	public void update(Pedido pedido) {
		
		try {
			//Java 13 - text block -  """   """
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE n2_ecommerce.tbpedidos SET IDSTATUS=?, " 
                    		                           + "IDUSUARIO=?, "
                    		                           + "IDENDERECO=?, "
                    		                           + "DATA=? "
                                                + "WHERE ID=?");
            
            // Parameters start with 1
            // preparedStatement previne SQL Injection...
            preparedStatement.setInt(1, pedido.getIdStatus());
            preparedStatement.setInt(2, pedido.getIdUsuario());
            preparedStatement.setInt(3, pedido.getIdEndereco());
            
            java.util.Date dt = pedido.getDataPedido();
            java.sql.Date d = new java.sql.Date (dt.getTime());
            
            preparedStatement.setDate(4, d);

            preparedStatement.setInt(5, pedido.getId());
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	} //update

	public void delete(int id) {

		try {
            
        	PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM n2_ecommerce.tbpedidos WHERE ID=?");
            
            // Parameters start with 1
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
        		e.printStackTrace();
        } //try
		
	} //delete 

	@Override
	public Pedido find(int id) {
		
		Pedido p = new Pedido();
        
    	try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM n2_ecommerce.tbpedidos WHERE ID=?");
            
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                p.setId(rs.getInt("ID"));
                p.setIdStatus(rs.getInt("IDSTATUS"));
                p.setIdUsuario(rs.getInt("IDUSUARIO"));
                p.setIdEndereco(rs.getInt("IDENDERECO"));
                p.setDataPedido(rs.getDate("DATA"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

        return p;
		
	} //find

	@Override
	public ArrayList<Pedido> findAll(Pedido pedido) {
		//Ajustar para enviar os dados de forma paginada, usar fun��o SQL "LIMIT" do MySQL
		
		ArrayList<Pedido> pList = new ArrayList<Pedido>();
        
        try {
        	
            Statement statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT * FROM n2_ecommerce.tbpedidos");
            
            while (rs.next()) {
                
            	Pedido p = new Pedido();
                
                p.setId(rs.getInt("ID"));
                p.setIdStatus(rs.getInt("IDSTATUS"));
                p.setIdUsuario(rs.getInt("IDUSUARIO"));
                p.setIdEndereco(rs.getInt("IDENDERECO"));
                p.setDataPedido(rs.getDate("DATA"));

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
                    prepareStatement("SELECT COUNT(1) QTD FROM n2_ecommerce.tbpedidos");
            
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
                    prepareStatement("SELECT MAX(ID) MAX_ID FROM n2_ecommerce.tbpedidos");
            
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("MAX_ID");
                
            } //if
            
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

		return maxId;
		
	} //maxId
	
	public String getId(Pedido pedido) {
		
		String id = "";
        
    	try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT ID FROM n2_ecommerce.tbpedidos WHERE DATA=?, IDSTATUS=?, IDENDERECO=?, IDUSUARIO=?");
            
            java.util.Date dt = pedido.getDataPedido();
            java.sql.Date d = new java.sql.Date (dt.getTime());
            
            preparedStatement.setDate(1, d);
            preparedStatement.setInt(2, pedido.getIdStatus());
            preparedStatement.setInt(3, pedido.getIdEndereco());
            preparedStatement.setInt(4, pedido.getIdUsuario());
            ResultSet rs = preparedStatement.executeQuery();
            


            if (rs.next()) {
                id = rs.getString("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

        return id;
		
	} //find

} //ProdutoDao
