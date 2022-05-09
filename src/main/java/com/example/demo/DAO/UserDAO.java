package com.example.demo.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            preparedStatement.setBoolean(7, user.isStatusUsuario());     
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
            preparedStatement.setBoolean(6, user.isStatusUsuario());
            preparedStatement.setString(7, user.getId());
            
            preparedStatement.executeUpdate();
            this.connection.close();
	} 

	@Override
	public void delete(String id) throws Exception {
        	PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM tbusuario WHERE ID=?");
            
            // Parameters start with 1
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            this.connection.close();
	} 

	@Override
	public User find(String string) throws Exception {
		
		User u = new User();
        
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM n2_ecommerce.tbusuario WHERE ID=?");
            
            preparedStatement.setString(1, string);
            ResultSet rs = preparedStatement.executeQuery();
            
            var data = this.FillObjectsFromResultSet(rs);
            if (data==null)
         	   return null;
            else
         	   return data.get(0);
		
	} 
	public User findByEmail(String email) throws Exception {
		
 		User u = new User();
        
        PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM n2_ecommerce.tbusuario WHERE email=?");
            
       preparedStatement.setString(1, email);
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
		
        Statement statement = connection.createStatement();
            
        ResultSet rs = statement.executeQuery("SELECT * FROM n2_ecommerce.tbusuario");
            
        return this.FillObjectsFromResultSet(rs);
		
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

