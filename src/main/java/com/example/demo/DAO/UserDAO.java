package com.example.demo.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.demo.models.EnderecoModel;
import com.example.demo.models.OrdensModel;
import com.example.demo.models.User;


public class UserDAO implements IRepositoryService<User> {
	
	private Connection connection;

	
	//Constructor
	public UserDAO() throws Exception {
		
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}
	
	@Override
	public ArrayList<User> FillObjectsFromResultSet(ResultSet rs) throws Exception {
		ArrayList<User> uList = new ArrayList<User>();
		if (!rs.next() ) {
    		this.connection.close();
    		return null;
    	} else {

    	    do {
            	User u = new User();
                
            	u.setId(rs.getString("ID"));
                u.setNome(rs.getString("NOME"));
                u.setEmail(rs.getString("EMAIL"));
                u.setSenha(rs.getString("SENHA"));
                u.setCpf(rs.getString("CPF"));               
                u.setIdTipoUsuario(rs.getString("IDTIPOUSUARIO"));              
                u.setStatusUsuario(rs.getBoolean("STATUSUSUARIO"));
                
                uList.add(u);
                
    	    } while (rs.next());
    	}
		this.connection.close();
		return uList;
	}

	@Override
	public void insert(User user) throws Exception {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO tbusuario "
                    		+ "(ID, NOME,  EMAIL, SENHA, CPF, IDTIPOUSUARIO, STATUSUSUARIO) "
                    		+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
            
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getNome());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getSenha());
            preparedStatement.setString(5, user.getCpf());          
            preparedStatement.setString(6, user.getIdTipoUsuario());
            preparedStatement.setBoolean(7, true);     
            preparedStatement.executeUpdate();	
            this.connection.close();
	}

	@Override
	public void update(User user) throws Exception{
		
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE tbusuario SET NOME=?, " 
                    		                           + "EMAIL=?, "
                    		                           + "SENHA=?, "
                    		                           + "CPF=?, "
                    		                           + "IDTIPOUSUARIO=?, " 
                    		                           + "STATUSUSUARIO=? "                     		                          
                                                + "WHERE ID=?");
            
            // Parameters start with 1
            // preparedStatement previne SQL Injection...
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getSenha());
            preparedStatement.setString(4, user.getCpf());
            preparedStatement.setString(5, user.getIdTipoUsuario());  
            preparedStatement.setBoolean(6, true);
            preparedStatement.setString(7, user.getId());
            
            preparedStatement.executeUpdate();
            this.connection.close();
	} 

	@Override
	public boolean delete(String id) throws Exception {
		
		connection.setAutoCommit(false);
		//pega todos pedidos em aberto do usuario e os cancela
		OrdensDAO odao= new OrdensDAO();
		var ordens=odao.findAllByUserAndStatus(id,"aoiuhwda23");
		if(ordens!=null)
		{
			for (OrdensModel ord:ordens)
			{
				PreparedStatement preparedStatement = connection
		                 .prepareStatement("UPDATE tbPedidos SET "
		                 		+ "idStatus='auihdbayuwidh2131313' where id=?");
		         preparedStatement.setString(1, ord.getId());
		         preparedStatement.executeUpdate();
			}
		}
		
		//pega on endereços do usuario		
		EnderecoDAO edao= new EnderecoDAO();
		var enderecos= edao.findAllAdressUser(id);
		
		if(enderecos!=null)
		{
			for(EnderecoModel end:enderecos)
			{
				PreparedStatement preparedStatement = connection
		                 .prepareStatement("UPDATE tbEnderecos SET "
		                 		+ "statusEnd=? where id=?");
		         preparedStatement.setBoolean(1, false);
		         preparedStatement.setString(2, end.getId());
		         preparedStatement.executeUpdate();
				
			}
		}
		
		PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE tbUsuario SET "
                		+ "statusUsuario=? where id=?");
        preparedStatement.setBoolean(1, false);
        preparedStatement.setString(2, id);
        
        preparedStatement.executeUpdate();
        connection.commit();
        this.connection.close();
        return true;
	} 

	@Override
	public User find(String string) throws Exception {
		
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM n2_ecommerce.tbusuario"
        		+ " WHERE ID=? and statusUsuario=?");
        
        ps.setString(1, string);
        ps.setBoolean(2, true);
        ResultSet newResultSet = ps.executeQuery();       
            
            var data = this.FillObjectsFromResultSet(newResultSet);
            if (data==null)
         	   return null;
            else
         	   return data.get(0);
		
	} 
	public User findByEmail(String email) throws Exception {
		
 		User u = new User();
        
        PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM n2_ecommerce.tbusuario WHERE email=? "
                    		+ " and statusUsuario=?");
            
       preparedStatement.setString(1, email);
       preparedStatement.setBoolean(2, true);
       ResultSet rs = preparedStatement.executeQuery();
       
       var data = this.FillObjectsFromResultSet(rs);
       
       if (data==null)
    	   return null;
       else
    	   return data.get(0);
		
	} 


	@Override
	public ArrayList<User> findAll() throws Exception{
		//Ajustar para enviar os dados de forma paginada, usar fun��o SQL "LIMIT" do MySQL
		
		   PreparedStatement ps = connection.prepareStatement("SELECT * FROM n2_ecommerce.tbusuario"
	        		+ " WHERE  statusUsuario=?");
	        
	        ps.setBoolean(1, true);
	        ResultSet newResultSet = ps.executeQuery();    
            
        return this.FillObjectsFromResultSet(newResultSet);
		
	} 
	
	public int count() {
		
		int count = -1;
		
		try {
			
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT COUNT(1) QTD FROM tbprodutos");
            
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                count = rs.getInt("QTD");
                this.connection.close();  
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
              this.connection.close();
            } //if
            
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

		return maxId;
		
	} //maxId

	
	

} //ProdutoDao

