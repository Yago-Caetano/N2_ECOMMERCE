package com.example.demo.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.example.demo.entity.User;
import com.example.demo.DAO.IRepositoryService;

public class UserDAO implements IRepositoryService<User> {
	
	private Connection connection;

	
	//Constructor
	public UserDAO() throws Exception {
		
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}

	@Override
	public void insert(User user) {

		try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO tbusuario (ID, NOME, NASCIMENTO, EMAIL, SENHA, CPF, IDTIPOUSUARIO, STATUSUSUARIO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            
            // Parameters start with 1
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getNome());
            preparedStatement.setDate(3, user.getNascimento());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getSenha());
            preparedStatement.setString(6, user.getCpf());          
            preparedStatement.setInt(7, user.getIdTipoUsuario());
            preparedStatement.setBoolean(8, user.isStatusUsuario());
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		
	}

	@Override
	public void update(User user) {
		
		try {
			//Java 13 - text block -  """   """
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE tbusuario SET NOME=?, " 
                    		                           + "NASCIMENTO=?, "
                    		                           + "EMAIL=?, "
                    		                           + "SENHA=?, "
                    		                           + "CPF=?, "
                    		                           + "IDTIPOUSUARIO=?, " 
                    		                           + "STATUSUSUARIO=?, "                     		                          
                                                + "WHERE ID=?");
            
            // Parameters start with 1
            // preparedStatement previne SQL Injection...
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setDate(2, user.getNascimento());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getSenha());
            preparedStatement.setString(5, user.getCpf());
            preparedStatement.setInt(6, user.getIdTipoUsuario());  
            preparedStatement.setBoolean(7, user.isStatusUsuario());

            preparedStatement.setInt(8, user.getId());
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	} 

	@Override
	public void delete(int id) {

		try {
            
        	PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM tbusuario WHERE ID=?");
            
            // Parameters start with 1
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
        		e.printStackTrace();
        } 
		
	} 

	@Override
	public User find(int id) {
		
		User u = new User();
        
    	try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM n2_ecommerce.tbusuario WHERE ID=?");
            
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                u.setId(rs.getInt("ID"));
                u.setNome(rs.getString("NOME"));
                u.setNascimento(rs.getDate("NASCIMENTO"));
                u.setEmail(rs.getString("EMAIL"));
                u.setSenha(rs.getString("SENHA"));
                u.setCpf(rs.getString("CPF"));               
                u.setIdTipoUsuario(rs.getInt("IDTIPOUSUARIO"));              
                u.setStatusUsuario(rs.getBoolean("STATUSUSUARIO"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return u;
		
	} 

	@Override
	public ArrayList<User> findAll() {
		//Ajustar para enviar os dados de forma paginada, usar fun��o SQL "LIMIT" do MySQL
		
		ArrayList<User> uList = new ArrayList<User>();
        
        try {
        	
            Statement statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT * FROM tbprodutos");
            
            while (rs.next()) {
                
            	User u = new User();
                
            	u.setId(rs.getInt("ID"));
                u.setNome(rs.getString("NOME"));
                u.setNascimento(rs.getDate("NASCIMENTO"));
                u.setEmail(rs.getString("EMAIL"));
                u.setSenha(rs.getString("SENHA"));
                u.setCpf(rs.getString("CPF"));               
                u.setIdTipoUsuario(rs.getInt("IDTIPOUSUARIO"));              
                u.setStatusUsuario(rs.getBoolean("STATUSUSUARIO"));

                uList.add(u);
                
            } 
            
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return uList;
		
	} 
	
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
	

} //ProdutoDao

