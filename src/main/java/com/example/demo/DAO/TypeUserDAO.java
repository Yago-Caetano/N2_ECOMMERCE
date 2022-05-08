package com.example.demo.DAO;
import com.example.demo.models.TypeUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TypeUserDAO implements IRepositoryService<TypeUser>  {
	
	private Connection connection;
	
	//Constructor
	public TypeUserDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}

	@Override
	public void insert(TypeUser obj) throws Exception {
		PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO tbTipoUsuario (Id,Tipo) VALUES (?,?)");
        
        // Parameters start with 1
        preparedStatement.setString(1, obj.getId());
        preparedStatement.setString(2, obj.getTipo());
        preparedStatement.executeUpdate();
        
        this.connection.close();
		
	}

	@Override
	public void update(TypeUser obj) throws Exception {
		 PreparedStatement preparedStatement = connection
                 .prepareStatement("UPDATE tbTipoUsuario SET Tipo=?  "  
                                             + "WHERE id=?");
         
         // Parameters start with 1
         // preparedStatement previne SQL Injection...
         preparedStatement.setString(1, obj.getTipo());
         preparedStatement.setString(2, obj.getId());          
         preparedStatement.executeUpdate();
         this.connection.close();
		
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TypeUser find(String id) throws Exception {
		TypeUser tipo = new TypeUser();

        PreparedStatement preparedStatement = connection.
                prepareStatement("SELECT * FROM n2_ecommerce.tbTipoUsuario WHERE id=?");
        
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
        	tipo.setId(rs.getString("id"));
        	tipo.setTipo(rs.getString("Tipo"));
        }
        this.connection.close();

    return tipo;
	}
	
	
	@Override
	public ArrayList<TypeUser> findAll() throws Exception {
		//Ajustar para enviar os dados de forma paginada, usar fun��o SQL "LIMIT" do MySQL
		
				ArrayList<TypeUser> pList = new ArrayList<TypeUser>();
		        

		            Statement statement = connection.createStatement();
		            
		            ResultSet rs = statement.executeQuery("SELECT * FROM tbTipoUsuario");
		            
		            while (rs.next()) {
		                
		            	TypeUser p = new TypeUser();
		                
		            	p.setId(rs.getString("id"));
		                p.setTipo(rs.getString("Tipo"));
		                pList.add(p);
		                
		            } //while
		            this.connection.close();
		            
		        return pList;
	}

	

} //TypeUserDAO
